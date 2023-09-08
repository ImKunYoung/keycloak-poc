package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ArtistCommentRepository;
import com.mycompany.myapp.service.ArtistCommentService;
import com.mycompany.myapp.service.dto.ArtistCommentDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ArtistComment}.
 */
@RestController
@RequestMapping("/api")
public class ArtistCommentResource {

    private final Logger log = LoggerFactory.getLogger(ArtistCommentResource.class);

    private static final String ENTITY_NAME = "artistComment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArtistCommentService artistCommentService;

    private final ArtistCommentRepository artistCommentRepository;

    public ArtistCommentResource(ArtistCommentService artistCommentService, ArtistCommentRepository artistCommentRepository) {
        this.artistCommentService = artistCommentService;
        this.artistCommentRepository = artistCommentRepository;
    }

    /**
     * {@code POST  /artist-comments} : Create a new artistComment.
     *
     * @param artistCommentDTO the artistCommentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new artistCommentDTO, or with status {@code 400 (Bad Request)} if the artistComment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/artist-comments")
    public ResponseEntity<ArtistCommentDTO> createArtistComment(@RequestBody ArtistCommentDTO artistCommentDTO) throws URISyntaxException {
        log.debug("REST request to save ArtistComment : {}", artistCommentDTO);
        if (artistCommentDTO.getId() != null) {
            throw new BadRequestAlertException("A new artistComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArtistCommentDTO result = artistCommentService.save(artistCommentDTO);
        return ResponseEntity
            .created(new URI("/api/artist-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /artist-comments/:id} : Updates an existing artistComment.
     *
     * @param id the id of the artistCommentDTO to save.
     * @param artistCommentDTO the artistCommentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artistCommentDTO,
     * or with status {@code 400 (Bad Request)} if the artistCommentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the artistCommentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/artist-comments/{id}")
    public ResponseEntity<ArtistCommentDTO> updateArtistComment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArtistCommentDTO artistCommentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ArtistComment : {}, {}", id, artistCommentDTO);
        if (artistCommentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artistCommentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artistCommentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ArtistCommentDTO result = artistCommentService.update(artistCommentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, artistCommentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /artist-comments/:id} : Partial updates given fields of an existing artistComment, field will ignore if it is null
     *
     * @param id the id of the artistCommentDTO to save.
     * @param artistCommentDTO the artistCommentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artistCommentDTO,
     * or with status {@code 400 (Bad Request)} if the artistCommentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the artistCommentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the artistCommentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/artist-comments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArtistCommentDTO> partialUpdateArtistComment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArtistCommentDTO artistCommentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ArtistComment partially : {}, {}", id, artistCommentDTO);
        if (artistCommentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artistCommentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artistCommentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArtistCommentDTO> result = artistCommentService.partialUpdate(artistCommentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, artistCommentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /artist-comments} : get all the artistComments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of artistComments in body.
     */
    @GetMapping("/artist-comments")
    public ResponseEntity<List<ArtistCommentDTO>> getAllArtistComments(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ArtistComments");
        Page<ArtistCommentDTO> page = artistCommentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /artist-comments/:id} : get the "id" artistComment.
     *
     * @param id the id of the artistCommentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the artistCommentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/artist-comments/{id}")
    public ResponseEntity<ArtistCommentDTO> getArtistComment(@PathVariable Long id) {
        log.debug("REST request to get ArtistComment : {}", id);
        Optional<ArtistCommentDTO> artistCommentDTO = artistCommentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(artistCommentDTO);
    }

    /**
     * {@code DELETE  /artist-comments/:id} : delete the "id" artistComment.
     *
     * @param id the id of the artistCommentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/artist-comments/{id}")
    public ResponseEntity<Void> deleteArtistComment(@PathVariable Long id) {
        log.debug("REST request to delete ArtistComment : {}", id);
        artistCommentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
