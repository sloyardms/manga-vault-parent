package com.sloyardms.mediaparser.api.plugin;

import com.sloyardms.mediaparser.api.dto.MediaMetadataDto;
import com.sloyardms.mediaparser.api.exceptions.MediaParserException;

import java.io.File;
import java.io.IOException;

public interface MediaParserPlugin {

    /**
     * Returns true if this plugin can parse the provided file.
     * @param file The implementation can look at filename, file extension or file content.
     * @return true if the plugin can parse the file, false otherwise
     * @throws IOException if reading the file fails
     */
    boolean canParse(File file) throws IOException;

    /**
     * Parses a media input file and returns a {@link MediaMetadataDto} DTO.
     * @param file the media file to parse
     * @return parsed {@link MediaMetadataDto}
     * @throws IOException if reading the file fails
     * @throws MediaParserException if parsing fails
     */
    MediaMetadataDto parse(File file) throws IOException, MediaParserException;

}
