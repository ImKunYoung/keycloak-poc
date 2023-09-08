package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ShipmentDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Shipment}.
 */
public interface ShipmentService {
    /**
     * Save a shipment.
     *
     * @param shipmentDTO the entity to save.
     * @return the persisted entity.
     */
    ShipmentDTO save(ShipmentDTO shipmentDTO);

    /**
     * Updates a shipment.
     *
     * @param shipmentDTO the entity to update.
     * @return the persisted entity.
     */
    ShipmentDTO update(ShipmentDTO shipmentDTO);

    /**
     * Partially updates a shipment.
     *
     * @param shipmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ShipmentDTO> partialUpdate(ShipmentDTO shipmentDTO);

    /**
     * Get all the shipments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ShipmentDTO> findAll(Pageable pageable);

    /**
     * Get all the shipments with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ShipmentDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" shipment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShipmentDTO> findOne(Long id);

    /**
     * Delete the "id" shipment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
