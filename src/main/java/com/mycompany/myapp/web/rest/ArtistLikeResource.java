package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ArtistLikeRepository;
import com.mycompany.myapp.service.ArtistLikeService;
import com.mycompany.myapp.service.dto.ArtistLikeDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ArtistLike}.
 */
@RestController
@RequestMapping("/api")
public class ArtistLikeResource {

    private final Logger log = LoggerFactory.getLogger(ArtistLikeResource.class);

    private static final String ENTITY_NAME = "artistLike";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArtistLikeService artistLikeService;

    private final ArtistLikeRepository artistLikeRepository;

    public ArtistLikeResource(ArtistLikeService artistLikeService, ArtistLikeRepository artistLikeRepository) {
        this.artistLikeService = artistLikeService;
        this.artistLikeRepository = artistLikeRepository;
    }

    /**
     * {@code POST  /artist-likes} : Create a new artistLike.
     *
     * @param artistLikeDTO the artistLikeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new artistLikeDTO, or with status {@code 400 (Bad Request)} if the artistLike has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/artist-likes")
    public ResponseEntity<ArtistLikeDTO> createArtistLike(@RequestBody ArtistLikeDTO artistLikeDTO) throws URISyntaxException {
        log.debug("REST request to save ArtistLike : {}", artistLikeDTO);
        if (artistLikeDTO.getId() != null) {
            throw new BadRequestAlertException("A new artistLike cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArtistLikeDTO result = artistLikeService.save(artistLikeDTO);
        return ResponseEntity
            .created(new URI("/api/artist-likes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /artist-likes/:id} : Updates an existing artistLike.
     *
     * @param id the id of the artistLikeDTO to save.
     * @param artistLikeDTO the artistLikeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artistLikeDTO,
     * or with status {@code 400 (Bad Request)} if the artistLikeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the artistLikeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/artist-likes/{id}")
    public ResponseEntity<ArtistLikeDTO> updateArtistLike(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArtistLikeDTO artistLikeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ArtistLike : {}, {}", id, artistLikeDTO);
        if (artistLikeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artistLikeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artistLikeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ArtistLikeDTO result = artistLikeService.update(artistLikeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, artistLikeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /artist-likes/:id} : Partial updates given fields of an existing artistLike, field will ignore if it is null
     *
     * @param id the id of the artistLikeDTO to save.
     * @param artistLikeDTO the artistLikeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artistLikeDTO,
     * or with status {@code 400 (Bad Request)} if the artistLikeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the artistLikeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the artistLikeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/artist-likes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArtistLikeDTO> partialUpdateArtistLike(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArtistLikeDTO artistLikeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ArtistLike partially : {}, {}", id, artistLikeDTO);
        if (artistLikeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artistLikeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artistLikeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArtistLikeDTO> result = artistLikeService.partialUpdate(artistLikeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, artistLikeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /artist-likes} : get all the artistLikes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of artistLikes in body.
     */
    @GetMapping("/artist-likes")
    public ResponseEntity<List<ArtistLikeDTO>> getAllArtistLikes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ArtistLikes");
        Page<ArtistLikeDTO> page = artistLikeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /artist-likes/:id} : get the "id" artistLike.
     *
     * @param id the id of the artistLikeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the artistLikeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/artist-likes/{id}")
    public ResponseEntity<ArtistLikeDTO> getArtistLike(@PathVariable Long id) {
        log.debug("REST request to get ArtistLike : {}", id);
        Optional<ArtistLikeDTO> artistLikeDTO = artistLikeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(artistLikeDTO);
    }

    /**
     * {@code DELETE  /artist-likes/:id} : delete the "id" artistLike.
     *
     * @param id the id of the artistLikeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/artist-likes/{id}")
    public ResponseEntity<Void> deleteArtistLike(@PathVariable Long id) {
        log.debug("REST request to delete ArtistLike : {}", id);
        artistLikeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
