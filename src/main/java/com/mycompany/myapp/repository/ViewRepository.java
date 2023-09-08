package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.View;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the View entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ViewRepository extends JpaRepository<View, Long> {}
