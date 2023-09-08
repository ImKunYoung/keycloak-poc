package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ArtworkLikeRepository;
import com.mycompany.myapp.service.ArtworkLikeService;
import com.mycompany.myapp.service.dto.ArtworkLikeDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ArtworkLike}.
 */
@RestController
@RequestMapping("/api")
public class ArtworkLikeResource {

    private final Logger log = LoggerFactory.getLogger(ArtworkLikeResource.class);

    private static final String ENTITY_NAME = "artworkLike";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArtworkLikeService artworkLikeService;

    private final ArtworkLikeRepository artworkLikeRepository;

    public ArtworkLikeResource(ArtworkLikeService artworkLikeService, ArtworkLikeRepository artworkLikeRepository) {
        this.artworkLikeService = artworkLikeService;
        this.artworkLikeRepository = artworkLikeRepository;
    }

    /**
     * {@code POST  /artwork-likes} : Create a new artworkLike.
     *
     * @param artworkLikeDTO the artworkLikeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new artworkLikeDTO, or with status {@code 400 (Bad Request)} if the artworkLike has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/artwork-likes")
    public ResponseEntity<ArtworkLikeDTO> createArtworkLike(@RequestBody ArtworkLikeDTO artworkLikeDTO) throws URISyntaxException {
        log.debug("REST request to save ArtworkLike : {}", artworkLikeDTO);
        if (artworkLikeDTO.getId() != null) {
            throw new BadRequestAlertException("A new artworkLike cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArtworkLikeDTO result = artworkLikeService.save(artworkLikeDTO);
        return ResponseEntity
            .created(new URI("/api/artwork-likes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /artwork-likes/:id} : Updates an existing artworkLike.
     *
     * @param id the id of the artworkLikeDTO to save.
     * @param artworkLikeDTO the artworkLikeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artworkLikeDTO,
     * or with status {@code 400 (Bad Request)} if the artworkLikeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the artworkLikeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/artwork-likes/{id}")
    public ResponseEntity<ArtworkLikeDTO> updateArtworkLike(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArtworkLikeDTO artworkLikeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ArtworkLike : {}, {}", id, artworkLikeDTO);
        if (artworkLikeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artworkLikeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artworkLikeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ArtworkLikeDTO result = artworkLikeService.update(artworkLikeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, artworkLikeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /artwork-likes/:id} : Partial updates given fields of an existing artworkLike, field will ignore if it is null
     *
     * @param id the id of the artworkLikeDTO to save.
     * @param artworkLikeDTO the artworkLikeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artworkLikeDTO,
     * or with status {@code 400 (Bad Request)} if the artworkLikeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the artworkLikeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the artworkLikeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/artwork-likes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArtworkLikeDTO> partialUpdateArtworkLike(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArtworkLikeDTO artworkLikeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ArtworkLike partially : {}, {}", id, artworkLikeDTO);
        if (artworkLikeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artworkLikeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artworkLikeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArtworkLikeDTO> result = artworkLikeService.partialUpdate(artworkLikeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, artworkLikeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /artwork-likes} : get all the artworkLikes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of artworkLikes in body.
     */
    @GetMapping("/artwork-likes")
    public ResponseEntity<List<ArtworkLikeDTO>> getAllArtworkLikes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ArtworkLikes");
        Page<ArtworkLikeDTO> page = artworkLikeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /artwork-likes/:id} : get the "id" artworkLike.
     *
     * @param id the id of the artworkLikeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the artworkLikeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/artwork-likes/{id}")
    public ResponseEntity<ArtworkLikeDTO> getArtworkLike(@PathVariable Long id) {
        log.debug("REST request to get ArtworkLike : {}", id);
        Optional<ArtworkLikeDTO> artworkLikeDTO = artworkLikeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(artworkLikeDTO);
    }

    /**
     * {@code DELETE  /artwork-likes/:id} : delete the "id" artworkLike.
     *
     * @param id the id of the artworkLikeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/artwork-likes/{id}")
    public ResponseEntity<Void> deleteArtworkLike(@PathVariable Long id) {
        log.debug("REST request to delete ArtworkLike : {}", id);
        artworkLikeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
