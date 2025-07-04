package com.sloyardms.mediaservice.repository;

import com.sloyardms.mediaservice.entity.MediaAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MediaAuthorRepository extends JpaRepository<MediaAuthor, Long> {

    /**
     * Finds all {@link MediaAuthor} entities where the {@code name} field matches any of the provided author names (case-insensitive).
     * <p>
     * Note: The input list should already contain lowercase author names, as the comparison uses {@code LOWER(mg.name)} in the query.
     *
     * @param namesLowerCase a list of author names in lowercase to match against
     * @return a list of matching {@link MediaAuthor} entities
     */
    @Query("SELECT ma FROM MediaAuthor ma WHERE LOWER(ma.name) IN :names")
    List<MediaAuthor> findAllByNameInIgnoreCase(@Param("names") List<String> namesLowerCase);

}
