package com.sloyardms.mediaservice.repository;

import com.sloyardms.mediaservice.models.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MediaRepository extends JpaRepository<Media, UUID> {

}
