package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ArtistViewRepository;
import com.mycompany.myapp.service.ArtistViewService;
import com.mycompany.myapp.service.dto.ArtistViewDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ArtistView}.
 */
@RestController
@RequestMapping("/api")
public class ArtistViewResource {

    private final Logger log = LoggerFactory.getLogger(ArtistViewResource.class);

    private static final String ENTITY_NAME = "artistView";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArtistViewService artistViewService;

    private final ArtistViewRepository artistViewRepository;

    public ArtistViewResource(ArtistViewService artistViewService, ArtistViewRepository artistViewRepository) {
        this.artistViewService = artistViewService;
        this.artistViewRepository = artistViewRepository;
    }

    /**
     * {@code POST  /artist-views} : Create a new artistView.
     *
     * @param artistViewDTO the artistViewDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new artistViewDTO, or with status {@code 400 (Bad Request)} if the artistView has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/artist-views")
    public ResponseEntity<ArtistViewDTO> createArtistView(@RequestBody ArtistViewDTO artistViewDTO) throws URISyntaxException {
        log.debug("REST request to save ArtistView : {}", artistViewDTO);
        if (artistViewDTO.getId() != null) {
            throw new BadRequestAlertException("A new artistView cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArtistViewDTO result = artistViewService.save(artistViewDTO);
        return ResponseEntity
            .created(new URI("/api/artist-views/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /artist-views/:id} : Updates an existing artistView.
     *
     * @param id the id of the artistViewDTO to save.
     * @param artistViewDTO the artistViewDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artistViewDTO,
     * or with status {@code 400 (Bad Request)} if the artistViewDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the artistViewDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/artist-views/{id}")
    public ResponseEntity<ArtistViewDTO> updateArtistView(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArtistViewDTO artistViewDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ArtistView : {}, {}", id, artistViewDTO);
        if (artistViewDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artistViewDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artistViewRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ArtistViewDTO result = artistViewService.update(artistViewDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, artistViewDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /artist-views/:id} : Partial updates given fields of an existing artistView, field will ignore if it is null
     *
     * @param id the id of the artistViewDTO to save.
     * @param artistViewDTO the artistViewDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artistViewDTO,
     * or with status {@code 400 (Bad Request)} if the artistViewDTO is not valid,
     * or with status {@code 404 (Not Found)} if the artistViewDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the artistViewDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/artist-views/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArtistViewDTO> partialUpdateArtistView(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArtistViewDTO artistViewDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ArtistView partially : {}, {}", id, artistViewDTO);
        if (artistViewDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artistViewDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artistViewRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArtistViewDTO> result = artistViewService.partialUpdate(artistViewDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, artistViewDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /artist-views} : get all the artistViews.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of artistViews in body.
     */
    @GetMapping("/artist-views")
    public ResponseEntity<List<ArtistViewDTO>> getAllArtistViews(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ArtistViews");
        Page<ArtistViewDTO> page = artistViewService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /artist-views/:id} : get the "id" artistView.
     *
     * @param id the id of the artistViewDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the artistViewDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/artist-views/{id}")
    public ResponseEntity<ArtistViewDTO> getArtistView(@PathVariable Long id) {
        log.debug("REST request to get ArtistView : {}", id);
        Optional<ArtistViewDTO> artistViewDTO = artistViewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(artistViewDTO);
    }

    /**
     * {@code DELETE  /artist-views/:id} : delete the "id" artistView.
     *
     * @param id the id of the artistViewDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/artist-views/{id}")
    public ResponseEntity<Void> deleteArtistView(@PathVariable Long id) {
        log.debug("REST request to delete ArtistView : {}", id);
        artistViewService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
