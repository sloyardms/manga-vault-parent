package com.sloyardms.mediaservice.controller;

import com.sloyardms.mediaservice.dto.MediaDetailResponse;
import com.sloyardms.mediaservice.dto.MediaSummaryResponse;
import com.sloyardms.mediaservice.mapper.MediaMapper;
import com.sloyardms.mediaservice.models.Media;
import com.sloyardms.mediaservice.projection.MediaSummaryProjection;
import com.sloyardms.mediaservice.service.interfaces.MediaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/media")
@Tag(name = "Media", description = "Media management API - v1")
public class WebtoonController {

    private final MediaService mediaService;
    private final MediaMapper mediaMapper;

    public WebtoonController(MediaService mediaService, MediaMapper mediaMapper) {
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
            @ApiResponse(responseCode = "404", description = "The media was not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<MediaDetailResponse> getById(@PathVariable("id") UUID id){
        Media media = mediaService.getById(id);
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

    @Operation(summary = "Delete a media by its id", description = "Delete a media by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "The media was deleted"),
            @ApiResponse(responseCode = "404", description = "The media was not found")
    })
    @DeleteMapping
    public ResponseEntity<MediaSummaryResponse> delete(UUID id){
        mediaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
