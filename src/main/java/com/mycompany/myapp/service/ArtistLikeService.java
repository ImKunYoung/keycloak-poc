package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ArtistLikeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.ArtistLike}.
 */
public interface ArtistLikeService {
    /**
     * Save a artistLike.
     *
     * @param artistLikeDTO the entity to save.
     * @return the persisted entity.
     */
    ArtistLikeDTO save(ArtistLikeDTO artistLikeDTO);

    /**
     * Updates a artistLike.
     *
     * @param artistLikeDTO the entity to update.
     * @return the persisted entity.
     */
    ArtistLikeDTO update(ArtistLikeDTO artistLikeDTO);

    /**
     * Partially updates a artistLike.
     *
     * @param artistLikeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ArtistLikeDTO> partialUpdate(ArtistLikeDTO artistLikeDTO);

    /**
     * Get all the artistLikes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArtistLikeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" artistLike.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArtistLikeDTO> findOne(Long id);

    /**
     * Delete the "id" artistLike.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
