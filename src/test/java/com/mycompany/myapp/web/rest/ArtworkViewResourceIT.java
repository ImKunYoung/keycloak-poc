package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ArtworkView;
import com.mycompany.myapp.repository.ArtworkViewRepository;
import com.mycompany.myapp.service.dto.ArtworkViewDTO;
import com.mycompany.myapp.service.mapper.ArtworkViewMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ArtworkViewResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArtworkViewResourceIT {

    private static final Long DEFAULT_MEMBER = 1L;
    private static final Long UPDATED_MEMBER = 2L;

    private static final String ENTITY_API_URL = "/api/artwork-views";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArtworkViewRepository artworkViewRepository;

    @Autowired
    private ArtworkViewMapper artworkViewMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArtworkViewMockMvc;

    private ArtworkView artworkView;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArtworkView createEntity(EntityManager em) {
        ArtworkView artworkView = new ArtworkView().member(DEFAULT_MEMBER);
        return artworkView;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArtworkView createUpdatedEntity(EntityManager em) {
        ArtworkView artworkView = new ArtworkView().member(UPDATED_MEMBER);
        return artworkView;
    }

    @BeforeEach
    public void initTest() {
        artworkView = createEntity(em);
    }

    @Test
    @Transactional
    void createArtworkView() throws Exception {
        int databaseSizeBeforeCreate = artworkViewRepository.findAll().size();
        // Create the ArtworkView
        ArtworkViewDTO artworkViewDTO = artworkViewMapper.toDto(artworkView);
        restArtworkViewMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artworkViewDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ArtworkView in the database
        List<ArtworkView> artworkViewList = artworkViewRepository.findAll();
        assertThat(artworkViewList).hasSize(databaseSizeBeforeCreate + 1);
        ArtworkView testArtworkView = artworkViewList.get(artworkViewList.size() - 1);
        assertThat(testArtworkView.getMember()).isEqualTo(DEFAULT_MEMBER);
    }

    @Test
    @Transactional
    void createArtworkViewWithExistingId() throws Exception {
        // Create the ArtworkView with an existing ID
        artworkView.setId(1L);
        ArtworkViewDTO artworkViewDTO = artworkViewMapper.toDto(artworkView);

        int databaseSizeBeforeCreate = artworkViewRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArtworkViewMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artworkViewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkView in the database
        List<ArtworkView> artworkViewList = artworkViewRepository.findAll();
        assertThat(artworkViewList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllArtworkViews() throws Exception {
        // Initialize the database
        artworkViewRepository.saveAndFlush(artworkView);

        // Get all the artworkViewList
        restArtworkViewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artworkView.getId().intValue())))
            .andExpect(jsonPath("$.[*].member").value(hasItem(DEFAULT_MEMBER.intValue())));
    }

    @Test
    @Transactional
    void getArtworkView() throws Exception {
        // Initialize the database
        artworkViewRepository.saveAndFlush(artworkView);

        // Get the artworkView
        restArtworkViewMockMvc
            .perform(get(ENTITY_API_URL_ID, artworkView.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(artworkView.getId().intValue()))
            .andExpect(jsonPath("$.member").value(DEFAULT_MEMBER.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingArtworkView() throws Exception {
        // Get the artworkView
        restArtworkViewMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArtworkView() throws Exception {
        // Initialize the database
        artworkViewRepository.saveAndFlush(artworkView);

        int databaseSizeBeforeUpdate = artworkViewRepository.findAll().size();

        // Update the artworkView
        ArtworkView updatedArtworkView = artworkViewRepository.findById(artworkView.getId()).get();
        // Disconnect from session so that the updates on updatedArtworkView are not directly saved in db
        em.detach(updatedArtworkView);
        updatedArtworkView.member(UPDATED_MEMBER);
        ArtworkViewDTO artworkViewDTO = artworkViewMapper.toDto(updatedArtworkView);

        restArtworkViewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artworkViewDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artworkViewDTO))
            )
            .andExpect(status().isOk());

        // Validate the ArtworkView in the database
        List<ArtworkView> artworkViewList = artworkViewRepository.findAll();
        assertThat(artworkViewList).hasSize(databaseSizeBeforeUpdate);
        ArtworkView testArtworkView = artworkViewList.get(artworkViewList.size() - 1);
        assertThat(testArtworkView.getMember()).isEqualTo(UPDATED_MEMBER);
    }

    @Test
    @Transactional
    void putNonExistingArtworkView() throws Exception {
        int databaseSizeBeforeUpdate = artworkViewRepository.findAll().size();
        artworkView.setId(count.incrementAndGet());

        // Create the ArtworkView
        ArtworkViewDTO artworkViewDTO = artworkViewMapper.toDto(artworkView);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtworkViewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artworkViewDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artworkViewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkView in the database
        List<ArtworkView> artworkViewList = artworkViewRepository.findAll();
        assertThat(artworkViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArtworkView() throws Exception {
        int databaseSizeBeforeUpdate = artworkViewRepository.findAll().size();
        artworkView.setId(count.incrementAndGet());

        // Create the ArtworkView
        ArtworkViewDTO artworkViewDTO = artworkViewMapper.toDto(artworkView);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkViewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artworkViewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkView in the database
        List<ArtworkView> artworkViewList = artworkViewRepository.findAll();
        assertThat(artworkViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArtworkView() throws Exception {
        int databaseSizeBeforeUpdate = artworkViewRepository.findAll().size();
        artworkView.setId(count.incrementAndGet());

        // Create the ArtworkView
        ArtworkViewDTO artworkViewDTO = artworkViewMapper.toDto(artworkView);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkViewMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artworkViewDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArtworkView in the database
        List<ArtworkView> artworkViewList = artworkViewRepository.findAll();
        assertThat(artworkViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArtworkViewWithPatch() throws Exception {
        // Initialize the database
        artworkViewRepository.saveAndFlush(artworkView);

        int databaseSizeBeforeUpdate = artworkViewRepository.findAll().size();

        // Update the artworkView using partial update
        ArtworkView partialUpdatedArtworkView = new ArtworkView();
        partialUpdatedArtworkView.setId(artworkView.getId());

        partialUpdatedArtworkView.member(UPDATED_MEMBER);

        restArtworkViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtworkView.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtworkView))
            )
            .andExpect(status().isOk());

        // Validate the ArtworkView in the database
        List<ArtworkView> artworkViewList = artworkViewRepository.findAll();
        assertThat(artworkViewList).hasSize(databaseSizeBeforeUpdate);
        ArtworkView testArtworkView = artworkViewList.get(artworkViewList.size() - 1);
        assertThat(testArtworkView.getMember()).isEqualTo(UPDATED_MEMBER);
    }

    @Test
    @Transactional
    void fullUpdateArtworkViewWithPatch() throws Exception {
        // Initialize the database
        artworkViewRepository.saveAndFlush(artworkView);

        int databaseSizeBeforeUpdate = artworkViewRepository.findAll().size();

        // Update the artworkView using partial update
        ArtworkView partialUpdatedArtworkView = new ArtworkView();
        partialUpdatedArtworkView.setId(artworkView.getId());

        partialUpdatedArtworkView.member(UPDATED_MEMBER);

        restArtworkViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtworkView.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtworkView))
            )
            .andExpect(status().isOk());

        // Validate the ArtworkView in the database
        List<ArtworkView> artworkViewList = artworkViewRepository.findAll();
        assertThat(artworkViewList).hasSize(databaseSizeBeforeUpdate);
        ArtworkView testArtworkView = artworkViewList.get(artworkViewList.size() - 1);
        assertThat(testArtworkView.getMember()).isEqualTo(UPDATED_MEMBER);
    }

    @Test
    @Transactional
    void patchNonExistingArtworkView() throws Exception {
        int databaseSizeBeforeUpdate = artworkViewRepository.findAll().size();
        artworkView.setId(count.incrementAndGet());

        // Create the ArtworkView
        ArtworkViewDTO artworkViewDTO = artworkViewMapper.toDto(artworkView);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtworkViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, artworkViewDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artworkViewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkView in the database
        List<ArtworkView> artworkViewList = artworkViewRepository.findAll();
        assertThat(artworkViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArtworkView() throws Exception {
        int databaseSizeBeforeUpdate = artworkViewRepository.findAll().size();
        artworkView.setId(count.incrementAndGet());

        // Create the ArtworkView
        ArtworkViewDTO artworkViewDTO = artworkViewMapper.toDto(artworkView);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artworkViewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkView in the database
        List<ArtworkView> artworkViewList = artworkViewRepository.findAll();
        assertThat(artworkViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArtworkView() throws Exception {
        int databaseSizeBeforeUpdate = artworkViewRepository.findAll().size();
        artworkView.setId(count.incrementAndGet());

        // Create the ArtworkView
        ArtworkViewDTO artworkViewDTO = artworkViewMapper.toDto(artworkView);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkViewMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artworkViewDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArtworkView in the database
        List<ArtworkView> artworkViewList = artworkViewRepository.findAll();
        assertThat(artworkViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArtworkView() throws Exception {
        // Initialize the database
        artworkViewRepository.saveAndFlush(artworkView);

        int databaseSizeBeforeDelete = artworkViewRepository.findAll().size();

        // Delete the artworkView
        restArtworkViewMockMvc
            .perform(delete(ENTITY_API_URL_ID, artworkView.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArtworkView> artworkViewList = artworkViewRepository.findAll();
        assertThat(artworkViewList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
