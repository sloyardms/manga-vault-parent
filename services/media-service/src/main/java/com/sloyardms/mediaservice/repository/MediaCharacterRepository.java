package com.sloyardms.mediaservice.repository;

import com.sloyardms.mediaservice.models.MediaCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MediaCharacterRepository extends JpaRepository<MediaCharacter, Long> {

    /**
     * Finds all {@link MediaCharacter} entities where the {@code name} field matches any of the provided character names (case-insensitive).
     * <p>
     * Note: The input list should already contain lowercase character names, as the comparison uses {@code LOWER(mg.name)} in the query.
     *
     * @param namesLowerCase a list of character names in lowercase to match against
     * @return a list of matching {@link MediaCharacter} entities
     */
    @Query("SELECT mc FROM MediaCharacter mc WHERE LOWER(mc.name) IN :names")
    List<MediaCharacter> findAllByNameInIgnoreCase(@Param("names") List<String> namesLowerCase);

}
