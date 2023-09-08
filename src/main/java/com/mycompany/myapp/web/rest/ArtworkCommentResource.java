package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ArtworkCommentRepository;
import com.mycompany.myapp.service.ArtworkCommentService;
import com.mycompany.myapp.service.dto.ArtworkCommentDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ArtworkComment}.
 */
@RestController
@RequestMapping("/api")
public class ArtworkCommentResource {

    private final Logger log = LoggerFactory.getLogger(ArtworkCommentResource.class);

    private static final String ENTITY_NAME = "artworkComment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArtworkCommentService artworkCommentService;

    private final ArtworkCommentRepository artworkCommentRepository;

    public ArtworkCommentResource(ArtworkCommentService artworkCommentService, ArtworkCommentRepository artworkCommentRepository) {
        this.artworkCommentService = artworkCommentService;
        this.artworkCommentRepository = artworkCommentRepository;
    }

    /**
     * {@code POST  /artwork-comments} : Create a new artworkComment.
     *
     * @param artworkCommentDTO the artworkCommentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new artworkCommentDTO, or with status {@code 400 (Bad Request)} if the artworkComment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/artwork-comments")
    public ResponseEntity<ArtworkCommentDTO> createArtworkComment(@RequestBody ArtworkCommentDTO artworkCommentDTO)
        throws URISyntaxException {
        log.debug("REST request to save ArtworkComment : {}", artworkCommentDTO);
        if (artworkCommentDTO.getId() != null) {
            throw new BadRequestAlertException("A new artworkComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArtworkCommentDTO result = artworkCommentService.save(artworkCommentDTO);
        return ResponseEntity
            .created(new URI("/api/artwork-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /artwork-comments/:id} : Updates an existing artworkComment.
     *
     * @param id the id of the artworkCommentDTO to save.
     * @param artworkCommentDTO the artworkCommentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artworkCommentDTO,
     * or with status {@code 400 (Bad Request)} if the artworkCommentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the artworkCommentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/artwork-comments/{id}")
    public ResponseEntity<ArtworkCommentDTO> updateArtworkComment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArtworkCommentDTO artworkCommentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ArtworkComment : {}, {}", id, artworkCommentDTO);
        if (artworkCommentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artworkCommentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artworkCommentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ArtworkCommentDTO result = artworkCommentService.update(artworkCommentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, artworkCommentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /artwork-comments/:id} : Partial updates given fields of an existing artworkComment, field will ignore if it is null
     *
     * @param id the id of the artworkCommentDTO to save.
     * @param artworkCommentDTO the artworkCommentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artworkCommentDTO,
     * or with status {@code 400 (Bad Request)} if the artworkCommentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the artworkCommentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the artworkCommentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/artwork-comments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArtworkCommentDTO> partialUpdateArtworkComment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArtworkCommentDTO artworkCommentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ArtworkComment partially : {}, {}", id, artworkCommentDTO);
        if (artworkCommentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artworkCommentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artworkCommentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArtworkCommentDTO> result = artworkCommentService.partialUpdate(artworkCommentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, artworkCommentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /artwork-comments} : get all the artworkComments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of artworkComments in body.
     */
    @GetMapping("/artwork-comments")
    public ResponseEntity<List<ArtworkCommentDTO>> getAllArtworkComments(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ArtworkComments");
        Page<ArtworkCommentDTO> page = artworkCommentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /artwork-comments/:id} : get the "id" artworkComment.
     *
     * @param id the id of the artworkCommentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the artworkCommentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/artwork-comments/{id}")
    public ResponseEntity<ArtworkCommentDTO> getArtworkComment(@PathVariable Long id) {
        log.debug("REST request to get ArtworkComment : {}", id);
        Optional<ArtworkCommentDTO> artworkCommentDTO = artworkCommentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(artworkCommentDTO);
    }

    /**
     * {@code DELETE  /artwork-comments/:id} : delete the "id" artworkComment.
     *
     * @param id the id of the artworkCommentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/artwork-comments/{id}")
    public ResponseEntity<Void> deleteArtworkComment(@PathVariable Long id) {
        log.debug("REST request to delete ArtworkComment : {}", id);
        artworkCommentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
