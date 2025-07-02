package com.sloyardms.mediaservice.repository;

import com.sloyardms.mediaservice.models.Media;
import com.sloyardms.mediaservice.projection.MediaSummaryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MediaRepository extends JpaRepository<Media, UUID> {

    Optional<MediaSummaryProjection> findSummaryById(UUID id);

    Page<MediaSummaryProjection> findAllSummaryBy(Pageable pageable);

    List<MediaSummaryProjection> findAllSummaryByIdOrMainTitle(UUID id, String mainTitle);

}
