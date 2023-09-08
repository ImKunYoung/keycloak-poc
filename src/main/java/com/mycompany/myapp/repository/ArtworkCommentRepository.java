package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ArtworkComment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ArtworkComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtworkCommentRepository extends JpaRepository<ArtworkComment, Long> {}
