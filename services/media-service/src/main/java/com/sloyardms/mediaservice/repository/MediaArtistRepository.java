package com.sloyardms.mediaservice.repository;

import com.sloyardms.mediaservice.models.MediaArtist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MediaArtistRepository extends JpaRepository<MediaArtist, Long> {

    /**
     * Finds all {@link MediaArtist} entities where the {@code name} field matches any of the provided artist names (case-insensitive).
     * <p>
     * Note: The input list should already contain lowercase artist names, as the comparison uses {@code LOWER(mg.name)} in the query.
     *
     * @param namesLowerCase a list of artist names in lowercase to match against
     * @return a list of matching {@link MediaArtist} entities
     */
    @Query("SELECT ma FROM MediaArtist ma WHERE LOWER(ma.name) IN :names")
    List<MediaArtist> findAllByNameInIgnoreCase(@Param("names") List<String> namesLowerCase);

}
