package com.sloyardms.mediaservice.repository;

import com.sloyardms.mediaservice.entity.MediaLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MediaLanguageRepository extends JpaRepository<MediaLanguage, Long> {

    Optional<MediaLanguage> findFirstByNameIgnoreCase(String name);

}
