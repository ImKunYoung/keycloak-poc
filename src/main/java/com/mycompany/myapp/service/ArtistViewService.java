package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ArtistViewDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.ArtistView}.
 */
public interface ArtistViewService {
    /**
     * Save a artistView.
     *
     * @param artistViewDTO the entity to save.
     * @return the persisted entity.
     */
    ArtistViewDTO save(ArtistViewDTO artistViewDTO);

    /**
     * Updates a artistView.
     *
     * @param artistViewDTO the entity to update.
     * @return the persisted entity.
     */
    ArtistViewDTO update(ArtistViewDTO artistViewDTO);

    /**
     * Partially updates a artistView.
     *
     * @param artistViewDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ArtistViewDTO> partialUpdate(ArtistViewDTO artistViewDTO);

    /**
     * Get all the artistViews.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArtistViewDTO> findAll(Pageable pageable);

    /**
     * Get the "id" artistView.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArtistViewDTO> findOne(Long id);

    /**
     * Delete the "id" artistView.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
