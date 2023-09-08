package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ArtistComment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ArtistComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtistCommentRepository extends JpaRepository<ArtistComment, Long> {}
