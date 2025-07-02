package com.sloyardms.mediaservice.repository;

import com.sloyardms.mediaservice.models.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MediaTypeRepository extends JpaRepository<MediaType, Long> {

    Optional<MediaType> findFirstByNameIgnoreCase(String name);

}
