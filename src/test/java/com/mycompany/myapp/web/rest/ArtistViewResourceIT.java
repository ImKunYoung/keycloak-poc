package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ArtistView;
import com.mycompany.myapp.repository.ArtistViewRepository;
import com.mycompany.myapp.service.dto.ArtistViewDTO;
import com.mycompany.myapp.service.mapper.ArtistViewMapper;
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
 * Integration tests for the {@link ArtistViewResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArtistViewResourceIT {

    private static final Long DEFAULT_VO_MEMBER = 1L;
    private static final Long UPDATED_VO_MEMBER = 2L;

    private static final String ENTITY_API_URL = "/api/artist-views";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArtistViewRepository artistViewRepository;

    @Autowired
    private ArtistViewMapper artistViewMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArtistViewMockMvc;

    private ArtistView artistView;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArtistView createEntity(EntityManager em) {
        ArtistView artistView = new ArtistView().voMember(DEFAULT_VO_MEMBER);
        return artistView;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArtistView createUpdatedEntity(EntityManager em) {
        ArtistView artistView = new ArtistView().voMember(UPDATED_VO_MEMBER);
        return artistView;
    }

    @BeforeEach
    public void initTest() {
        artistView = createEntity(em);
    }

    @Test
    @Transactional
    void createArtistView() throws Exception {
        int databaseSizeBeforeCreate = artistViewRepository.findAll().size();
        // Create the ArtistView
        ArtistViewDTO artistViewDTO = artistViewMapper.toDto(artistView);
        restArtistViewMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artistViewDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ArtistView in the database
        List<ArtistView> artistViewList = artistViewRepository.findAll();
        assertThat(artistViewList).hasSize(databaseSizeBeforeCreate + 1);
        ArtistView testArtistView = artistViewList.get(artistViewList.size() - 1);
        assertThat(testArtistView.getVoMember()).isEqualTo(DEFAULT_VO_MEMBER);
    }

    @Test
    @Transactional
    void createArtistViewWithExistingId() throws Exception {
        // Create the ArtistView with an existing ID
        artistView.setId(1L);
        ArtistViewDTO artistViewDTO = artistViewMapper.toDto(artistView);

        int databaseSizeBeforeCreate = artistViewRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArtistViewMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artistViewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtistView in the database
        List<ArtistView> artistViewList = artistViewRepository.findAll();
        assertThat(artistViewList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllArtistViews() throws Exception {
        // Initialize the database
        artistViewRepository.saveAndFlush(artistView);

        // Get all the artistViewList
        restArtistViewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artistView.getId().intValue())))
            .andExpect(jsonPath("$.[*].voMember").value(hasItem(DEFAULT_VO_MEMBER.intValue())));
    }

    @Test
    @Transactional
    void getArtistView() throws Exception {
        // Initialize the database
        artistViewRepository.saveAndFlush(artistView);

        // Get the artistView
        restArtistViewMockMvc
            .perform(get(ENTITY_API_URL_ID, artistView.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(artistView.getId().intValue()))
            .andExpect(jsonPath("$.voMember").value(DEFAULT_VO_MEMBER.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingArtistView() throws Exception {
        // Get the artistView
        restArtistViewMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArtistView() throws Exception {
        // Initialize the database
        artistViewRepository.saveAndFlush(artistView);

        int databaseSizeBeforeUpdate = artistViewRepository.findAll().size();

        // Update the artistView
        ArtistView updatedArtistView = artistViewRepository.findById(artistView.getId()).get();
        // Disconnect from session so that the updates on updatedArtistView are not directly saved in db
        em.detach(updatedArtistView);
        updatedArtistView.voMember(UPDATED_VO_MEMBER);
        ArtistViewDTO artistViewDTO = artistViewMapper.toDto(updatedArtistView);

        restArtistViewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artistViewDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artistViewDTO))
            )
            .andExpect(status().isOk());

        // Validate the ArtistView in the database
        List<ArtistView> artistViewList = artistViewRepository.findAll();
        assertThat(artistViewList).hasSize(databaseSizeBeforeUpdate);
        ArtistView testArtistView = artistViewList.get(artistViewList.size() - 1);
        assertThat(testArtistView.getVoMember()).isEqualTo(UPDATED_VO_MEMBER);
    }

    @Test
    @Transactional
    void putNonExistingArtistView() throws Exception {
        int databaseSizeBeforeUpdate = artistViewRepository.findAll().size();
        artistView.setId(count.incrementAndGet());

        // Create the ArtistView
        ArtistViewDTO artistViewDTO = artistViewMapper.toDto(artistView);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtistViewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artistViewDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artistViewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtistView in the database
        List<ArtistView> artistViewList = artistViewRepository.findAll();
        assertThat(artistViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArtistView() throws Exception {
        int databaseSizeBeforeUpdate = artistViewRepository.findAll().size();
        artistView.setId(count.incrementAndGet());

        // Create the ArtistView
        ArtistViewDTO artistViewDTO = artistViewMapper.toDto(artistView);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtistViewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artistViewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtistView in the database
        List<ArtistView> artistViewList = artistViewRepository.findAll();
        assertThat(artistViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArtistView() throws Exception {
        int databaseSizeBeforeUpdate = artistViewRepository.findAll().size();
        artistView.setId(count.incrementAndGet());

        // Create the ArtistView
        ArtistViewDTO artistViewDTO = artistViewMapper.toDto(artistView);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtistViewMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artistViewDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArtistView in the database
        List<ArtistView> artistViewList = artistViewRepository.findAll();
        assertThat(artistViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArtistViewWithPatch() throws Exception {
        // Initialize the database
        artistViewRepository.saveAndFlush(artistView);

        int databaseSizeBeforeUpdate = artistViewRepository.findAll().size();

        // Update the artistView using partial update
        ArtistView partialUpdatedArtistView = new ArtistView();
        partialUpdatedArtistView.setId(artistView.getId());

        partialUpdatedArtistView.voMember(UPDATED_VO_MEMBER);

        restArtistViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtistView.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtistView))
            )
            .andExpect(status().isOk());

        // Validate the ArtistView in the database
        List<ArtistView> artistViewList = artistViewRepository.findAll();
        assertThat(artistViewList).hasSize(databaseSizeBeforeUpdate);
        ArtistView testArtistView = artistViewList.get(artistViewList.size() - 1);
        assertThat(testArtistView.getVoMember()).isEqualTo(UPDATED_VO_MEMBER);
    }

    @Test
    @Transactional
    void fullUpdateArtistViewWithPatch() throws Exception {
        // Initialize the database
        artistViewRepository.saveAndFlush(artistView);

        int databaseSizeBeforeUpdate = artistViewRepository.findAll().size();

        // Update the artistView using partial update
        ArtistView partialUpdatedArtistView = new ArtistView();
        partialUpdatedArtistView.setId(artistView.getId());

        partialUpdatedArtistView.voMember(UPDATED_VO_MEMBER);

        restArtistViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtistView.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtistView))
            )
            .andExpect(status().isOk());

        // Validate the ArtistView in the database
        List<ArtistView> artistViewList = artistViewRepository.findAll();
        assertThat(artistViewList).hasSize(databaseSizeBeforeUpdate);
        ArtistView testArtistView = artistViewList.get(artistViewList.size() - 1);
        assertThat(testArtistView.getVoMember()).isEqualTo(UPDATED_VO_MEMBER);
    }

    @Test
    @Transactional
    void patchNonExistingArtistView() throws Exception {
        int databaseSizeBeforeUpdate = artistViewRepository.findAll().size();
        artistView.setId(count.incrementAndGet());

        // Create the ArtistView
        ArtistViewDTO artistViewDTO = artistViewMapper.toDto(artistView);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtistViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, artistViewDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artistViewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtistView in the database
        List<ArtistView> artistViewList = artistViewRepository.findAll();
        assertThat(artistViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArtistView() throws Exception {
        int databaseSizeBeforeUpdate = artistViewRepository.findAll().size();
        artistView.setId(count.incrementAndGet());

        // Create the ArtistView
        ArtistViewDTO artistViewDTO = artistViewMapper.toDto(artistView);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtistViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artistViewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtistView in the database
        List<ArtistView> artistViewList = artistViewRepository.findAll();
        assertThat(artistViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArtistView() throws Exception {
        int databaseSizeBeforeUpdate = artistViewRepository.findAll().size();
        artistView.setId(count.incrementAndGet());

        // Create the ArtistView
        ArtistViewDTO artistViewDTO = artistViewMapper.toDto(artistView);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtistViewMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artistViewDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArtistView in the database
        List<ArtistView> artistViewList = artistViewRepository.findAll();
        assertThat(artistViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArtistView() throws Exception {
        // Initialize the database
        artistViewRepository.saveAndFlush(artistView);

        int databaseSizeBeforeDelete = artistViewRepository.findAll().size();

        // Delete the artistView
        restArtistViewMockMvc
            .perform(delete(ENTITY_API_URL_ID, artistView.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArtistView> artistViewList = artistViewRepository.findAll();
        assertThat(artistViewList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
