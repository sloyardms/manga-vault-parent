package com.sloyardms.mediaservice.repository;

import com.sloyardms.mediaservice.models.MediaTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MediaTagRepository extends JpaRepository<MediaTag, Long> {

    /**
     * Finds all {@link MediaTag} entities where the {@code name} field matches any of the provided tag names (case-insensitive).
     * <p>
     * Note: The input list should already contain lowercase tag names, as the comparison uses {@code LOWER(mg.name)} in the query.
     *
     * @param namesLowerCase a list of tag names in lowercase to match against
     * @return a list of matching {@link MediaTag} entities
     */
    @Query("SELECT mt FROM MediaTag mt WHERE LOWER(mt.name) IN :names")
    List<MediaTag> findAllByNameInIgnoreCase(@Param("names") List<String> namesLowerCase);

}
