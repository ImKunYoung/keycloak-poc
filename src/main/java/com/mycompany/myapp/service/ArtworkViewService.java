package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ArtworkViewDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.ArtworkView}.
 */
public interface ArtworkViewService {
    /**
     * Save a artworkView.
     *
     * @param artworkViewDTO the entity to save.
     * @return the persisted entity.
     */
    ArtworkViewDTO save(ArtworkViewDTO artworkViewDTO);

    /**
     * Updates a artworkView.
     *
     * @param artworkViewDTO the entity to update.
     * @return the persisted entity.
     */
    ArtworkViewDTO update(ArtworkViewDTO artworkViewDTO);

    /**
     * Partially updates a artworkView.
     *
     * @param artworkViewDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ArtworkViewDTO> partialUpdate(ArtworkViewDTO artworkViewDTO);

    /**
     * Get all the artworkViews.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArtworkViewDTO> findAll(Pageable pageable);

    /**
     * Get the "id" artworkView.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArtworkViewDTO> findOne(Long id);

    /**
     * Delete the "id" artworkView.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
