package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ArtistLike;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ArtistLike entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtistLikeRepository extends JpaRepository<ArtistLike, Long> {}
