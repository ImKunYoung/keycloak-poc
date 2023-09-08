package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ArtworkViewRepository;
import com.mycompany.myapp.service.ArtworkViewService;
import com.mycompany.myapp.service.dto.ArtworkViewDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.ArtworkView}.
 */
@RestController
@RequestMapping("/api")
public class ArtworkViewResource {

    private final Logger log = LoggerFactory.getLogger(ArtworkViewResource.class);

    private static final String ENTITY_NAME = "artworkView";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArtworkViewService artworkViewService;

    private final ArtworkViewRepository artworkViewRepository;

    public ArtworkViewResource(ArtworkViewService artworkViewService, ArtworkViewRepository artworkViewRepository) {
        this.artworkViewService = artworkViewService;
        this.artworkViewRepository = artworkViewRepository;
    }

    /**
     * {@code POST  /artwork-views} : Create a new artworkView.
     *
     * @param artworkViewDTO the artworkViewDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new artworkViewDTO, or with status {@code 400 (Bad Request)} if the artworkView has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/artwork-views")
    public ResponseEntity<ArtworkViewDTO> createArtworkView(@RequestBody ArtworkViewDTO artworkViewDTO) throws URISyntaxException {
        log.debug("REST request to save ArtworkView : {}", artworkViewDTO);
        if (artworkViewDTO.getId() != null) {
            throw new BadRequestAlertException("A new artworkView cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArtworkViewDTO result = artworkViewService.save(artworkViewDTO);
        return ResponseEntity
            .created(new URI("/api/artwork-views/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /artwork-views/:id} : Updates an existing artworkView.
     *
     * @param id the id of the artworkViewDTO to save.
     * @param artworkViewDTO the artworkViewDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artworkViewDTO,
     * or with status {@code 400 (Bad Request)} if the artworkViewDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the artworkViewDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/artwork-views/{id}")
    public ResponseEntity<ArtworkViewDTO> updateArtworkView(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArtworkViewDTO artworkViewDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ArtworkView : {}, {}", id, artworkViewDTO);
        if (artworkViewDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artworkViewDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artworkViewRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ArtworkViewDTO result = artworkViewService.update(artworkViewDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, artworkViewDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /artwork-views/:id} : Partial updates given fields of an existing artworkView, field will ignore if it is null
     *
     * @param id the id of the artworkViewDTO to save.
     * @param artworkViewDTO the artworkViewDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artworkViewDTO,
     * or with status {@code 400 (Bad Request)} if the artworkViewDTO is not valid,
     * or with status {@code 404 (Not Found)} if the artworkViewDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the artworkViewDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/artwork-views/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArtworkViewDTO> partialUpdateArtworkView(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArtworkViewDTO artworkViewDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ArtworkView partially : {}, {}", id, artworkViewDTO);
        if (artworkViewDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artworkViewDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artworkViewRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArtworkViewDTO> result = artworkViewService.partialUpdate(artworkViewDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, artworkViewDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /artwork-views} : get all the artworkViews.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of artworkViews in body.
     */
    @GetMapping("/artwork-views")
    public ResponseEntity<List<ArtworkViewDTO>> getAllArtworkViews(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ArtworkViews");
        Page<ArtworkViewDTO> page = artworkViewService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /artwork-views/:id} : get the "id" artworkView.
     *
     * @param id the id of the artworkViewDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the artworkViewDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/artwork-views/{id}")
    public ResponseEntity<ArtworkViewDTO> getArtworkView(@PathVariable Long id) {
        log.debug("REST request to get ArtworkView : {}", id);
        Optional<ArtworkViewDTO> artworkViewDTO = artworkViewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(artworkViewDTO);
    }

    /**
     * {@code DELETE  /artwork-views/:id} : delete the "id" artworkView.
     *
     * @param id the id of the artworkViewDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/artwork-views/{id}")
    public ResponseEntity<Void> deleteArtworkView(@PathVariable Long id) {
        log.debug("REST request to delete ArtworkView : {}", id);
        artworkViewService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
