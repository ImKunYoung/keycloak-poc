package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ArtistCommentDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.ArtistComment}.
 */
public interface ArtistCommentService {
    /**
     * Save a artistComment.
     *
     * @param artistCommentDTO the entity to save.
     * @return the persisted entity.
     */
    ArtistCommentDTO save(ArtistCommentDTO artistCommentDTO);

    /**
     * Updates a artistComment.
     *
     * @param artistCommentDTO the entity to update.
     * @return the persisted entity.
     */
    ArtistCommentDTO update(ArtistCommentDTO artistCommentDTO);

    /**
     * Partially updates a artistComment.
     *
     * @param artistCommentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ArtistCommentDTO> partialUpdate(ArtistCommentDTO artistCommentDTO);

    /**
     * Get all the artistComments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArtistCommentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" artistComment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArtistCommentDTO> findOne(Long id);

    /**
     * Delete the "id" artistComment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
