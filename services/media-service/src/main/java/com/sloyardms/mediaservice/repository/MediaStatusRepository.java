package com.sloyardms.mediaservice.repository;

import com.sloyardms.mediaservice.models.MediaStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MediaStatusRepository extends JpaRepository<MediaStatus, Long> {

    Optional<MediaStatus> findFirstByNameIgnoreCase(String name);

}
