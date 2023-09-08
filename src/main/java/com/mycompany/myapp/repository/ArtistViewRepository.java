package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ArtistView;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ArtistView entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtistViewRepository extends JpaRepository<ArtistView, Long> {}
