package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ArtworkRepository;
import com.mycompany.myapp.service.ArtworkService;
import com.mycompany.myapp.service.dto.ArtworkDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Artwork}.
 */
@RestController
@RequestMapping("/api")
public class ArtworkResource {

    private final Logger log = LoggerFactory.getLogger(ArtworkResource.class);

    private static final String ENTITY_NAME = "artwork";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArtworkService artworkService;

    private final ArtworkRepository artworkRepository;

    public ArtworkResource(ArtworkService artworkService, ArtworkRepository artworkRepository) {
        this.artworkService = artworkService;
        this.artworkRepository = artworkRepository;
    }

    /**
     * {@code POST  /artworks} : Create a new artwork.
     *
     * @param artworkDTO the artworkDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new artworkDTO, or with status {@code 400 (Bad Request)} if the artwork has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/artworks")
    public ResponseEntity<ArtworkDTO> createArtwork(@RequestBody ArtworkDTO artworkDTO) throws URISyntaxException {
        log.debug("REST request to save Artwork : {}", artworkDTO);
        if (artworkDTO.getId() != null) {
            throw new BadRequestAlertException("A new artwork cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArtworkDTO result = artworkService.save(artworkDTO);
        return ResponseEntity
            .created(new URI("/api/artworks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /artworks/:id} : Updates an existing artwork.
     *
     * @param id the id of the artworkDTO to save.
     * @param artworkDTO the artworkDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artworkDTO,
     * or with status {@code 400 (Bad Request)} if the artworkDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the artworkDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/artworks/{id}")
    public ResponseEntity<ArtworkDTO> updateArtwork(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArtworkDTO artworkDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Artwork : {}, {}", id, artworkDTO);
        if (artworkDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artworkDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artworkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ArtworkDTO result = artworkService.update(artworkDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, artworkDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /artworks/:id} : Partial updates given fields of an existing artwork, field will ignore if it is null
     *
     * @param id the id of the artworkDTO to save.
     * @param artworkDTO the artworkDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artworkDTO,
     * or with status {@code 400 (Bad Request)} if the artworkDTO is not valid,
     * or with status {@code 404 (Not Found)} if the artworkDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the artworkDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/artworks/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArtworkDTO> partialUpdateArtwork(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArtworkDTO artworkDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Artwork partially : {}, {}", id, artworkDTO);
        if (artworkDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artworkDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artworkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArtworkDTO> result = artworkService.partialUpdate(artworkDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, artworkDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /artworks} : get all the artworks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of artworks in body.
     */
    @GetMapping("/artworks")
    public ResponseEntity<List<ArtworkDTO>> getAllArtworks(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Artworks");
        Page<ArtworkDTO> page = artworkService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /artworks/:id} : get the "id" artwork.
     *
     * @param id the id of the artworkDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the artworkDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/artworks/{id}")
    public ResponseEntity<ArtworkDTO> getArtwork(@PathVariable Long id) {
        log.debug("REST request to get Artwork : {}", id);
        Optional<ArtworkDTO> artworkDTO = artworkService.findOne(id);
        return ResponseUtil.wrapOrNotFound(artworkDTO);
    }

    /**
     * {@code DELETE  /artworks/:id} : delete the "id" artwork.
     *
     * @param id the id of the artworkDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/artworks/{id}")
    public ResponseEntity<Void> deleteArtwork(@PathVariable Long id) {
        log.debug("REST request to delete Artwork : {}", id);
        artworkService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
