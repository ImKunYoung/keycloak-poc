package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ArtworkLikeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.ArtworkLike}.
 */
public interface ArtworkLikeService {
    /**
     * Save a artworkLike.
     *
     * @param artworkLikeDTO the entity to save.
     * @return the persisted entity.
     */
    ArtworkLikeDTO save(ArtworkLikeDTO artworkLikeDTO);

    /**
     * Updates a artworkLike.
     *
     * @param artworkLikeDTO the entity to update.
     * @return the persisted entity.
     */
    ArtworkLikeDTO update(ArtworkLikeDTO artworkLikeDTO);

    /**
     * Partially updates a artworkLike.
     *
     * @param artworkLikeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ArtworkLikeDTO> partialUpdate(ArtworkLikeDTO artworkLikeDTO);

    /**
     * Get all the artworkLikes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArtworkLikeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" artworkLike.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArtworkLikeDTO> findOne(Long id);

    /**
     * Delete the "id" artworkLike.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
