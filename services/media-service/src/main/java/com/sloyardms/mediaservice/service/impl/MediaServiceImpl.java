package com.sloyardms.mediaservice.service.impl;

import com.sloyardms.mediaservice.dto.request.UpdateMediaRequest;
import com.sloyardms.mediaservice.exception.InvalidResourceVersionException;
import com.sloyardms.mediaservice.exception.ResourceDuplicatedException;
import com.sloyardms.mediaservice.exception.ResourceNotFoundException;
import com.sloyardms.mediaservice.kafka.events.CreateMediaCommand;
import com.sloyardms.mediaservice.entity.*;
import com.sloyardms.mediaservice.projection.MediaSummaryProjection;
import com.sloyardms.mediaservice.repository.*;
import com.sloyardms.mediaservice.service.interfaces.MediaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Implementation of {@link MediaService} for managing Media entities.
 * This service handles creation (via kafka), retrieval, update, and deletion of {@link Media},
 * along with resolution of related entities such as {@link MediaAlternateTitle},{@link MediaArtist},
 * {@link MediaAuthor}, {@link MediaCharacter}, {@link MediaParody} and {@link MediaTag}
 */
@Service
public class MediaServiceImpl implements MediaService {

    private static final Logger logger = LoggerFactory.getLogger(MediaServiceImpl.class);
    private final MediaRepository mediaRepository;
    private final MediaStatusRepository mediaStatusRepository;
    private final MediaTypeRepository mediaTypeRepository;
    private final MediaParodyRepository mediaParodyRepository;
    private final MediaLanguageRepository mediaLanguageRepository;
    private final MediaAuthorRepository mediaAuthorRepository;
    private final MediaArtistRepository mediaArtistRepository;
    private final MediaTagRepository mediaTagRepository;
    private final MediaCharacterRepository mediaCharacterRepository;

    public MediaServiceImpl(MediaRepository mediaRepository,
                            MediaStatusRepository mediaStatusRepository, MediaTypeRepository mediaTypeRepository,
                            MediaParodyRepository mediaParodyRepository, MediaLanguageRepository mediaLanguageRepository,
                            MediaAuthorRepository mediaAuthorRepository, MediaArtistRepository mediaArtistRepository,
                            MediaTagRepository mediaTagRepository, MediaCharacterRepository mediaCharacterRepository) {
        this.mediaRepository = mediaRepository;
        this.mediaStatusRepository = mediaStatusRepository;
        this.mediaTypeRepository = mediaTypeRepository;
        this.mediaParodyRepository = mediaParodyRepository;
        this.mediaLanguageRepository = mediaLanguageRepository;
        this.mediaAuthorRepository = mediaAuthorRepository;
        this.mediaArtistRepository = mediaArtistRepository;
        this.mediaTagRepository = mediaTagRepository;
        this.mediaCharacterRepository = mediaCharacterRepository;
    }

    /**
     * Creates a new Media entity based on the provided {@link CreateMediaCommand}
     * <p>
     * Verifies that no other media exists with the same ID or main title to prevent duplicates.
     * The method then delegates the creation logic to a version-specific handler based on the command's version.
     * <p>
     * Currently, only version 1 is supported.
     * <p>
     * This method is transactional and will roll back all database changes if any exception occurs.
     *
     * @param createMediaCommand the command received from kafka for media creation
     * @return the created {@link Media} entity
     * @throws ResourceDuplicatedException     if a media with the same {@code id} or {@code mainTitle} exists
     * @throws InvalidResourceVersionException if the command has an unsupported version
     * @throws DataIntegrityViolationException if any database constraint is violated during persistence
     */
    @Override
    @Transactional(timeout = 30, rollbackFor = Exception.class)
    public Media create(CreateMediaCommand createMediaCommand) throws InvalidResourceVersionException, ResourceDuplicatedException, DataIntegrityViolationException {

        //Search for duplicate id or mainTitle
        List<MediaSummaryProjection> duplicatedMedia = mediaRepository.findAllSummaryByIdOrMainTitle(createMediaCommand.getId(), createMediaCommand.getMainTitle());
        if (!duplicatedMedia.isEmpty()) {
            String errorMessage = "Duplicate media id " + createMediaCommand.getId() + " or main title " + createMediaCommand.getMainTitle();
            logger.error(errorMessage);
            throw new ResourceDuplicatedException(errorMessage);
        }

        if (createMediaCommand.getVersion() == 1) {
            return handleMediaCreationV1(createMediaCommand);
        }

        throw new InvalidResourceVersionException("Invalid version for media creation: " + createMediaCommand.getVersion());
    }

    /**
     * Handles the creation of media entity based on version 1 of the {@link CreateMediaCommand} specification.
     * <p>
     * This method resolves and maps all required associated entities (authors, artists, tags, characters, etc.)
     * <p>
     * Assumes that all necessary validations (e.g., duplicates, version check) have already been performed before
     * calling this method
     *
     * @param createMediaCommand the command object containing media creation data
     * @return the newly created Media entity persisted in the database
     * @throws DataIntegrityViolationException if a database constraint violation occurs during save
     * @throws RuntimeException                if any unexpected error occurs during entity resolution or persistence
     */
    private Media handleMediaCreationV1(CreateMediaCommand createMediaCommand) {
        //Create a media entity and map simple properties
        Media media = new Media();
        media.setId(Optional.ofNullable(createMediaCommand.getId()).orElse(UUID.randomUUID()));
        media.setMainTitle(createMediaCommand.getMainTitle());
        media.setSummary(createMediaCommand.getSummary());
        media.setReleaseYear(createMediaCommand.getReleaseYear());
        media.setThumbnailUrl(createMediaCommand.getThumbnailUrl());
        media.setSourceUrl(createMediaCommand.getSourceUrl());

        //Set status, type, parody, language
        media.setStatus(resolveStatus(createMediaCommand.getMediaStatusName()));
        media.setType(resolveMediaType(createMediaCommand.getMediaTypeName()));
        media.setMediaParody(resolveMediaParody(createMediaCommand.getMediaParodyName()));
        media.setMediaLanguage(resolveMediaLanguage(createMediaCommand.getMediaLanguageName()));

        //map MediaAlternateTitle
        Set<MediaAlternateTitle> mediaAlternateTitles = resolveMediaAlternateTitles(createMediaCommand.getMediaAlternateTitles(), media);
        media.setMediaAlternateTitles(mediaAlternateTitles);

        //find and map MediaAuthors
        Set<MediaAuthor> mediaAuthors = resolveEntitySet(
                createMediaCommand.getMediaAuthors(),
                mediaAuthorRepository::findAllByNameInIgnoreCase,
                name -> {
                    MediaAuthor author = new MediaAuthor();
                    author.setName(name);
                    return author;
                },
                mediaAuthorRepository::saveAll,
                MediaAuthor::getName
        );
        media.setMediaAuthors(mediaAuthors);

        //find and map MediaArtists
        Set<MediaArtist> mediaArtists = resolveEntitySet(
                createMediaCommand.getMediaArtists(),
                mediaArtistRepository::findAllByNameInIgnoreCase,
                name -> {
                    MediaArtist artist = new MediaArtist();
                    artist.setName(name);
                    return artist;
                },
                mediaArtistRepository::saveAll,
                MediaArtist::getName
        );
        media.setMediaArtists(mediaArtists);

        //find and map MediaTags
        Set<MediaTag> mediaTags = resolveEntitySet(
                createMediaCommand.getMediaTags(),
                mediaTagRepository::findAllByNameInIgnoreCase,
                name -> {
                    MediaTag tag = new MediaTag();
                    tag.setName(name);
                    return tag;
                },
                mediaTagRepository::saveAll,
                MediaTag::getName
        );
        media.setMediaTags(mediaTags);

        //find and map MediaCharacters
        Set<MediaCharacter> mediaCharacters = resolveEntitySet(
                createMediaCommand.getMediaCharacters(),
                mediaCharacterRepository::findAllByNameInIgnoreCase,
                name -> {
                    MediaCharacter character = new MediaCharacter();
                    character.setName(name);
                    return character;
                },
                mediaCharacterRepository::saveAll,
                MediaCharacter::getName
        );
        media.setMediaCharacters(mediaCharacters);

        //Save the media
        try {
            Media savedMedia =  mediaRepository.save(media);
            logger.info("Successfully created media with ID: {}", createMediaCommand.getId());
            return savedMedia;
        } catch (DataIntegrityViolationException e) {
            logger.error("Data integrity issue while saving media", e);
            throw e;
        }
    }

    /**
     * Retrieves a {@link Media} entity by its UUID.
     *
     * @param id the UUID of the media to retrieve.
     * @return the Media entity.
     * @throws ResourceNotFoundException if the media is not found.
     */
    @Override
    @Transactional(readOnly = true, timeout = 30)
    public Media getById(UUID id) {
        return mediaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Media with id " + id + " not found"));
    }

    /**
     * Retrieves a paginated list of {@link Media} summaries.
     *
     * @param pageable the pagination and sorting information.
     * @return a Page of {@link MediaSummaryProjection}
     */
    @Override
    @Transactional(readOnly = true, timeout = 60)
    public Page<MediaSummaryProjection> getAll(Pageable pageable) {
        return mediaRepository.findAllSummaryBy(pageable);
    }

    /**
     * Updates a {@link Media} entity. Not implemented yet.
     *
     * @param id    the UUID of the media to update.
     * @param updateMediaRequest the media data to update.
     * @return updated media.
     */
    @Override
    @Transactional(timeout = 30, rollbackFor = Exception.class)
    public Media update(UUID id, UpdateMediaRequest updateMediaRequest) {
        return null;
    }

    /**
     * Deletes a {@link Media} by ID if it exists.
     *
     * @param id the UUID of the media to delete.
     * @throws ResourceNotFoundException if the media does not exist.
     */
    @Override
    @Transactional(timeout = 30, rollbackFor = Exception.class)
    public void delete(UUID id) {
        Optional<MediaSummaryProjection> mediaFound = mediaRepository.findSummaryById(id);
        if (mediaFound.isPresent()) {
            mediaRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Media with id " + id + " not found");
        }
    }

    @Override
    public Media uploadThumbnail(UUID id, MultipartFile file) {
        Media media = mediaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Media with id " + id + " not found"));

        // Save the file somewhere
        //TODO: add call to file-storage-system
        String thumbnailPath = "null";

        media.setThumbnailUrl(thumbnailPath);
        return mediaRepository.save(media);
    }

    /**
     * Finds a {@link MediaStatus} entity by name.
     *
     * @param statusName the name of the status.
     * @return the resolved MediaStatus entity.
     * @throws ResourceNotFoundException Media status not found
     */
    private MediaStatus resolveStatus(String statusName) throws ResourceNotFoundException {
        return mediaStatusRepository.findFirstByNameIgnoreCase(statusName).orElseThrow(() -> new ResourceNotFoundException("Media status with name " + statusName + " not found"));
    }

    /**
     * Finds a {@link MediaType} entity by name.
     *
     * @param mediaTypeName the name of the media type.
     * @return the resolved MediaType entity.
     * @throws ResourceNotFoundException Media type not found
     */
    private MediaType resolveMediaType(String mediaTypeName) throws ResourceNotFoundException {
        return mediaTypeRepository.findFirstByNameIgnoreCase(mediaTypeName).orElseThrow(() -> new ResourceNotFoundException("Media type with name " + mediaTypeName + " not found"));
    }

    /**
     * Finds a {@link MediaParody} entity by name.
     *
     * @param mediaParodyName the name of the parody.
     * @return the resolved MediaParody entity.
     */
    private MediaParody resolveMediaParody(String mediaParodyName) {
        return mediaParodyRepository.findFirstByNameIgnoreCase(mediaParodyName).orElseGet(() -> {
            MediaParody newParody = new MediaParody();
            newParody.setName(mediaParodyName);
            return mediaParodyRepository.save(newParody);
        });
    }

    /**
     * Finds a {@link MediaLanguage} entity by name.
     *
     * @param mediaLanguageName the name of the language.
     * @return the resolved MediaLanguage entity.
     * @throws ResourceNotFoundException Media language not found
     */
    private MediaLanguage resolveMediaLanguage(String mediaLanguageName) {
        return mediaLanguageRepository.findFirstByNameIgnoreCase(mediaLanguageName).orElseThrow(() -> new ResourceNotFoundException("Media language with name " + mediaLanguageName + " not found"));
    }

    /**
     * Maps a set of alternate title strings to {@link MediaAlternateTitle} entities associated with the given media.
     *
     * @param mediaAlternateTitles the set of alternate titles.
     * @param media                the media to associate with the alternate titles (with id).
     * @return a set of MediaAlternateTitle entities.
     */
    private Set<MediaAlternateTitle> resolveMediaAlternateTitles(Set<String> mediaAlternateTitles, Media media) {
        Set<MediaAlternateTitle> alternateTitles = new HashSet<>();
        if (mediaAlternateTitles != null) {
            for (String altTitleString : mediaAlternateTitles) {
                MediaAlternateTitle alternateTitle = new MediaAlternateTitle();
                alternateTitle.setTitle(altTitleString);
                alternateTitle.setMedia(media);
                alternateTitles.add(alternateTitle);
            }
        }
        return alternateTitles;
    }

    /**
     * Resolves a set of related entities by checking if they already exist, creating and saving any missing ones,
     * and returning the complete set
     *
     * @param inputNames          A set of strings representing the names of the desired entities.
     * @param findExistingByNames Function to find existing entities by a list of names (repository method)
     * @param createEntity        Function that creates a new entity given its name
     * @param saveAllEntities     Consumer that persists in the list of newly created entities (repository method)
     * @param nameExtractor       Function that extracts the name from an entity
     * @param <E>                 The type of the entity
     * @return A set containing both existing and newly created entities
     */
    public <E> Set<E> resolveEntitySet(
            Set<String> inputNames,
            Function<List<String>, List<E>> findExistingByNames,
            Function<String, E> createEntity,
            Consumer<List<E>> saveAllEntities,
            Function<E, String> nameExtractor
    ) {
        List<String> lowerCaseInput = inputNames.stream().map(String::toLowerCase).toList();

        List<E> existingEntities = findExistingByNames.apply(lowerCaseInput);
        Set<String> existingNames = existingEntities.stream()
                .map(e -> nameExtractor.apply(e).toLowerCase())
                .collect(Collectors.toSet());

        List<E> newEntities = lowerCaseInput.stream()
                .filter(name -> !existingNames.contains(name))
                .map(createEntity)
                .toList();

        saveAllEntities.accept(newEntities);

        Set<E> result = new HashSet<>(newEntities);
        result.addAll(existingEntities);
        return result;
    }

}
