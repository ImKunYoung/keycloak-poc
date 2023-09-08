package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Artwork;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Artwork entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {}
