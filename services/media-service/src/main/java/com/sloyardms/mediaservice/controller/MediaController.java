package com.sloyardms.mediaservice.controller;

import com.sloyardms.mediaservice.dto.request.UpdateMediaRequest;
import com.sloyardms.mediaservice.dto.response.MediaDetailResponse;
import com.sloyardms.mediaservice.dto.response.MediaSummaryResponse;
import com.sloyardms.mediaservice.mapper.MediaMapper;
import com.sloyardms.mediaservice.entity.Media;
import com.sloyardms.mediaservice.projection.MediaSummaryProjection;
import com.sloyardms.mediaservice.service.interfaces.MediaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/v1/media")
@Tag(name = "Media", description = "Media management API - v1")
public class MediaController {

    private final MediaService mediaService;
    private final MediaMapper mediaMapper;

    public MediaController(MediaService mediaService, MediaMapper mediaMapper) {
        this.mediaService = mediaService;
        this.mediaMapper = mediaMapper;
    }

    @Operation(summary = "Ping the server", description = "Ping the server")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "pong", content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/ping")
    public ResponseEntity<String> ping(){
        return ResponseEntity.ok("pong");
    }

    @Operation(summary = "Get a media by its id", description = "Get a media by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The media was found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MediaDetailResponse.class))),
            @ApiResponse(responseCode = "404", description = "Media not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<MediaDetailResponse> getById(
            @Parameter(description = "UUID of the media", required = true)
            @PathVariable("uuid") UUID uuid){
        Media media = mediaService.getById(uuid);
        MediaDetailResponse response = mediaMapper.toDetailResponse(media);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all media", description = "Get all media")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The page with the results", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MediaSummaryResponse.class))))
    })
    @GetMapping
    public ResponseEntity<Page<MediaSummaryResponse>> getAll(Pageable pageable){
        Page<MediaSummaryProjection> results = mediaService.getAll(pageable);
        return ResponseEntity.ok(results.map(mediaMapper::toSummaryResponse));
    }

    @Operation(summary = "Update an existing media", description = "Updates fields of an existing media by its UUID. Only non-null fields will be updated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Media updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MediaSummaryResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Media not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
    })
    @PatchMapping("/{uuid}")
    public ResponseEntity<MediaSummaryResponse> update(
            @Parameter(description = "UUID of the media", required = true)
            @PathVariable("uuid") UUID uuid, @Valid @RequestBody UpdateMediaRequest updateMediaRequest){
        Media media = mediaService.update(uuid, updateMediaRequest);
        MediaSummaryResponse response = mediaMapper.toSummaryResponse(media);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a media by its id", description = "Delete a media by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Media deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Media not found")
    })
    @DeleteMapping("/{uuid}")
    public ResponseEntity<MediaSummaryResponse> delete(
            @Parameter(description = "UUID of the media", required = true)
            @PathVariable("uuid") UUID uuid){
        mediaService.delete(uuid);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{uuid}/thumbnail", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Update the thumbnail image for a media",
            description = "Accepts a new image file to replace the current thumbnail. Returns the updated media with the new thumbnail URL."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Thumbnail uploaded and media updated successfully",
                    content = @Content(schema = @Schema(implementation = MediaSummaryResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid image file", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Media not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<MediaSummaryResponse> uploadThumbnail(
            @Parameter(description = "UUID of the media", required = true)
            @PathVariable("uuid") UUID uuid,

            @Parameter(description = "Thumbnail image file (JPG/PNG)", required = true)
            @RequestPart("file") MultipartFile file
    ) {
        Media media = mediaService.uploadThumbnail(uuid, file);
        MediaSummaryResponse response = mediaMapper.toSummaryResponse(media);
        return ResponseEntity.ok(response);
    }
}
