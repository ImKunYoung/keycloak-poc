package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ArtworkLike;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ArtworkLike entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtworkLikeRepository extends JpaRepository<ArtworkLike, Long> {}
