package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ArtworkView;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ArtworkView entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtworkViewRepository extends JpaRepository<ArtworkView, Long> {}
