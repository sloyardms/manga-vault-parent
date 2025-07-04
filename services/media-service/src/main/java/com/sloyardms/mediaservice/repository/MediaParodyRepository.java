package com.sloyardms.mediaservice.repository;

import com.sloyardms.mediaservice.entity.MediaParody;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MediaParodyRepository extends JpaRepository<MediaParody, Long> {

    Optional<MediaParody> findFirstByNameIgnoreCase(String name);

}
