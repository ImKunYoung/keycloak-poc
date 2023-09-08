package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ExhibitionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Exhibition}.
 */
public interface ExhibitionService {
    /**
     * Save a exhibition.
     *
     * @param exhibitionDTO the entity to save.
     * @return the persisted entity.
     */
    ExhibitionDTO save(ExhibitionDTO exhibitionDTO);

    /**
     * Updates a exhibition.
     *
     * @param exhibitionDTO the entity to update.
     * @return the persisted entity.
     */
    ExhibitionDTO update(ExhibitionDTO exhibitionDTO);

    /**
     * Partially updates a exhibition.
     *
     * @param exhibitionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ExhibitionDTO> partialUpdate(ExhibitionDTO exhibitionDTO);

    /**
     * Get all the exhibitions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ExhibitionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" exhibition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExhibitionDTO> findOne(Long id);

    /**
     * Delete the "id" exhibition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
