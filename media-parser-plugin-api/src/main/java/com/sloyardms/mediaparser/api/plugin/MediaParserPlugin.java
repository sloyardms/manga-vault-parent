package com.sloyardms.mediaparser.api.plugin;

import com.sloyardms.mediaparser.api.dto.MediaMetadataDto;
import com.sloyardms.mediaparser.api.exceptions.MediaParserException;

import java.io.File;

public interface MediaParserPlugin {

    /**
     * Returns true if this plugin can parse the provided file.
     * @param folder The implementation will look at the files to check if it can parse the media folder
     * @return true if the plugin can parse the file, false otherwise
     */
    boolean canParse(File folder);

    /**
     * Parses a media input file and returns a {@link MediaMetadataDto} DTO.
     * @param folder the media folder to parse
     * @return parsed {@link MediaMetadataDto}
     * @throws MediaParserException if parsing fails
     */
    MediaMetadataDto parse(File folder) throws MediaParserException;

}
