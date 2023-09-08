package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ExhibitionRepository;
import com.mycompany.myapp.service.ExhibitionService;
import com.mycompany.myapp.service.dto.ExhibitionDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Exhibition}.
 */
@RestController
@RequestMapping("/api")
public class ExhibitionResource {

    private final Logger log = LoggerFactory.getLogger(ExhibitionResource.class);

    private static final String ENTITY_NAME = "exhibition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExhibitionService exhibitionService;

    private final ExhibitionRepository exhibitionRepository;

    public ExhibitionResource(ExhibitionService exhibitionService, ExhibitionRepository exhibitionRepository) {
        this.exhibitionService = exhibitionService;
        this.exhibitionRepository = exhibitionRepository;
    }

    /**
     * {@code POST  /exhibitions} : Create a new exhibition.
     *
     * @param exhibitionDTO the exhibitionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new exhibitionDTO, or with status {@code 400 (Bad Request)} if the exhibition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/exhibitions")
    public ResponseEntity<ExhibitionDTO> createExhibition(@RequestBody ExhibitionDTO exhibitionDTO) throws URISyntaxException {
        log.debug("REST request to save Exhibition : {}", exhibitionDTO);
        if (exhibitionDTO.getId() != null) {
            throw new BadRequestAlertException("A new exhibition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExhibitionDTO result = exhibitionService.save(exhibitionDTO);
        return ResponseEntity
            .created(new URI("/api/exhibitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /exhibitions/:id} : Updates an existing exhibition.
     *
     * @param id the id of the exhibitionDTO to save.
     * @param exhibitionDTO the exhibitionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated exhibitionDTO,
     * or with status {@code 400 (Bad Request)} if the exhibitionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the exhibitionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/exhibitions/{id}")
    public ResponseEntity<ExhibitionDTO> updateExhibition(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ExhibitionDTO exhibitionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Exhibition : {}, {}", id, exhibitionDTO);
        if (exhibitionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, exhibitionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!exhibitionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ExhibitionDTO result = exhibitionService.update(exhibitionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, exhibitionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /exhibitions/:id} : Partial updates given fields of an existing exhibition, field will ignore if it is null
     *
     * @param id the id of the exhibitionDTO to save.
     * @param exhibitionDTO the exhibitionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated exhibitionDTO,
     * or with status {@code 400 (Bad Request)} if the exhibitionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the exhibitionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the exhibitionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/exhibitions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ExhibitionDTO> partialUpdateExhibition(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ExhibitionDTO exhibitionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Exhibition partially : {}, {}", id, exhibitionDTO);
        if (exhibitionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, exhibitionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!exhibitionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ExhibitionDTO> result = exhibitionService.partialUpdate(exhibitionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, exhibitionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /exhibitions} : get all the exhibitions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of exhibitions in body.
     */
    @GetMapping("/exhibitions")
    public ResponseEntity<List<ExhibitionDTO>> getAllExhibitions(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Exhibitions");
        Page<ExhibitionDTO> page = exhibitionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /exhibitions/:id} : get the "id" exhibition.
     *
     * @param id the id of the exhibitionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the exhibitionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/exhibitions/{id}")
    public ResponseEntity<ExhibitionDTO> getExhibition(@PathVariable Long id) {
        log.debug("REST request to get Exhibition : {}", id);
        Optional<ExhibitionDTO> exhibitionDTO = exhibitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(exhibitionDTO);
    }

    /**
     * {@code DELETE  /exhibitions/:id} : delete the "id" exhibition.
     *
     * @param id the id of the exhibitionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/exhibitions/{id}")
    public ResponseEntity<Void> deleteExhibition(@PathVariable Long id) {
        log.debug("REST request to delete Exhibition : {}", id);
        exhibitionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
