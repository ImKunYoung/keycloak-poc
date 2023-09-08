package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ArtworkCommentDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.ArtworkComment}.
 */
public interface ArtworkCommentService {
    /**
     * Save a artworkComment.
     *
     * @param artworkCommentDTO the entity to save.
     * @return the persisted entity.
     */
    ArtworkCommentDTO save(ArtworkCommentDTO artworkCommentDTO);

    /**
     * Updates a artworkComment.
     *
     * @param artworkCommentDTO the entity to update.
     * @return the persisted entity.
     */
    ArtworkCommentDTO update(ArtworkCommentDTO artworkCommentDTO);

    /**
     * Partially updates a artworkComment.
     *
     * @param artworkCommentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ArtworkCommentDTO> partialUpdate(ArtworkCommentDTO artworkCommentDTO);

    /**
     * Get all the artworkComments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArtworkCommentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" artworkComment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArtworkCommentDTO> findOne(Long id);

    /**
     * Delete the "id" artworkComment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
