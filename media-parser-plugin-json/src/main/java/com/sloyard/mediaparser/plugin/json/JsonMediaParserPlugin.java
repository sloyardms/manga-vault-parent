package com.sloyard.mediaparser.plugin.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sloyardms.mediaparser.api.dto.MediaMetadataDto;
import com.sloyardms.mediaparser.api.exceptions.MediaParserException;
import com.sloyardms.mediaparser.api.plugin.MediaParserPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

public class JsonMediaParserPlugin implements MediaParserPlugin {

    private static final Logger log = LoggerFactory.getLogger(JsonMediaParserPlugin.class);

    /** ObjectMapper instance for JSON parsing */
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /** Supported image file extensions (lowercase, without dot) */
    public static final Set<String> IMAGE_FORMATS = Set.of("jpg", "jpeg", "png", "webp");

    /** The expected JSON metadata file name in the media folder */
    private static final String JSON_FILE_EXTENSION = "json";

    /** The expected chapters subfolder name in the media folder */
    private static final String CHAPTERS_FOLDER_NAME = "chapters";

    @Override
    public boolean canParse(File folder) {
        if(folder == null || !folder.exists() || !folder.isDirectory()){
            log.error("Folder doesnt exists or is not a directory: {}", folder);
            return false;
        }

        File[] files = folder.listFiles();
        if(files == null || files.length == 0){
            log.error("Folder is empty: {}", folder);
            return false;
        }

        boolean hasImage = false;
        boolean hasJsonFile = false;

        for(File file: files){
            if(isImageFile(file)){
                hasImage = true;
            } else if(isJsonFile(file)){
                hasJsonFile = true;
            }
            if(hasImage && hasJsonFile){
                break;
            }
        }

        boolean hasChaptersFolder = new File(folder, CHAPTERS_FOLDER_NAME).isDirectory();

        log.info("Has image: {}", hasImage);
        log.info("Has json file: {}", hasJsonFile);
        log.info("Has chapters folder: {}", hasChaptersFolder);

        return hasImage && hasJsonFile && hasChaptersFolder;
    }

    @Override
    public MediaMetadataDto parse(File folder) throws MediaParserException {

        if(folder==null || !folder.exists() || !folder.isDirectory()){
            throw new MediaParserException("Invalid folder");
        }

        File[] folderFiles = folder.listFiles();
        if(folderFiles == null || folderFiles.length == 0){
            throw new MediaParserException("Folder is empty: "+folder.getAbsolutePath());
        }

        File jsonFile = Arrays.stream(folderFiles)
                .filter(this::isJsonFile)
                .findFirst()
                .orElse(null);

        if(jsonFile == null){
            throw new MediaParserException("No JSON file found in folder: "+folder.getAbsolutePath());
        }

        try {
            return objectMapper.readValue(jsonFile, MediaMetadataDto.class);
        }catch (IOException e){
            throw new MediaParserException("Error parsing JSON file: "+jsonFile.getAbsolutePath(), e);
        }
    }

    /**
     * Checks if the given file is a valid image file by its extensions
     * @param file the file to check, not null
     * @return {@code true} if the file extension is in {@link #IMAGE_FORMATS}; {@code false} otherwise
     */
    private boolean isImageFile(File file){
        String name = file.getName().toLowerCase();
        int dotIndex = name.lastIndexOf('.');
        return dotIndex != -1 && IMAGE_FORMATS.contains(name.substring(dotIndex+1));
    }

    /**
     * Checks if the given file is the JSON metadata file
     * @param file the file to check, not null
     * @return {@code true} if the file name ends with {@value #JSON_FILE_EXTENSION} ignoring case; {@code false} otherwise
     */
    private boolean isJsonFile(File file){
        return file.getName().trim().endsWith(JSON_FILE_EXTENSION);
    }
}
