package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Exhibition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Exhibition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExhibitionRepository extends JpaRepository<Exhibition, Long> {}
