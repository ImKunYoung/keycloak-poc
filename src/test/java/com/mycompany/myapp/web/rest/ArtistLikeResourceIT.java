package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ArtistLike;
import com.mycompany.myapp.repository.ArtistLikeRepository;
import com.mycompany.myapp.service.dto.ArtistLikeDTO;
import com.mycompany.myapp.service.mapper.ArtistLikeMapper;
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
 * Integration tests for the {@link ArtistLikeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArtistLikeResourceIT {

    private static final Long DEFAULT_VO_MEMBER = 1L;
    private static final Long UPDATED_VO_MEMBER = 2L;

    private static final String ENTITY_API_URL = "/api/artist-likes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArtistLikeRepository artistLikeRepository;

    @Autowired
    private ArtistLikeMapper artistLikeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArtistLikeMockMvc;

    private ArtistLike artistLike;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArtistLike createEntity(EntityManager em) {
        ArtistLike artistLike = new ArtistLike().voMember(DEFAULT_VO_MEMBER);
        return artistLike;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArtistLike createUpdatedEntity(EntityManager em) {
        ArtistLike artistLike = new ArtistLike().voMember(UPDATED_VO_MEMBER);
        return artistLike;
    }

    @BeforeEach
    public void initTest() {
        artistLike = createEntity(em);
    }

    @Test
    @Transactional
    void createArtistLike() throws Exception {
        int databaseSizeBeforeCreate = artistLikeRepository.findAll().size();
        // Create the ArtistLike
        ArtistLikeDTO artistLikeDTO = artistLikeMapper.toDto(artistLike);
        restArtistLikeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artistLikeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ArtistLike in the database
        List<ArtistLike> artistLikeList = artistLikeRepository.findAll();
        assertThat(artistLikeList).hasSize(databaseSizeBeforeCreate + 1);
        ArtistLike testArtistLike = artistLikeList.get(artistLikeList.size() - 1);
        assertThat(testArtistLike.getVoMember()).isEqualTo(DEFAULT_VO_MEMBER);
    }

    @Test
    @Transactional
    void createArtistLikeWithExistingId() throws Exception {
        // Create the ArtistLike with an existing ID
        artistLike.setId(1L);
        ArtistLikeDTO artistLikeDTO = artistLikeMapper.toDto(artistLike);

        int databaseSizeBeforeCreate = artistLikeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArtistLikeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artistLikeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtistLike in the database
        List<ArtistLike> artistLikeList = artistLikeRepository.findAll();
        assertThat(artistLikeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllArtistLikes() throws Exception {
        // Initialize the database
        artistLikeRepository.saveAndFlush(artistLike);

        // Get all the artistLikeList
        restArtistLikeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artistLike.getId().intValue())))
            .andExpect(jsonPath("$.[*].voMember").value(hasItem(DEFAULT_VO_MEMBER.intValue())));
    }

    @Test
    @Transactional
    void getArtistLike() throws Exception {
        // Initialize the database
        artistLikeRepository.saveAndFlush(artistLike);

        // Get the artistLike
        restArtistLikeMockMvc
            .perform(get(ENTITY_API_URL_ID, artistLike.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(artistLike.getId().intValue()))
            .andExpect(jsonPath("$.voMember").value(DEFAULT_VO_MEMBER.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingArtistLike() throws Exception {
        // Get the artistLike
        restArtistLikeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArtistLike() throws Exception {
        // Initialize the database
        artistLikeRepository.saveAndFlush(artistLike);

        int databaseSizeBeforeUpdate = artistLikeRepository.findAll().size();

        // Update the artistLike
        ArtistLike updatedArtistLike = artistLikeRepository.findById(artistLike.getId()).get();
        // Disconnect from session so that the updates on updatedArtistLike are not directly saved in db
        em.detach(updatedArtistLike);
        updatedArtistLike.voMember(UPDATED_VO_MEMBER);
        ArtistLikeDTO artistLikeDTO = artistLikeMapper.toDto(updatedArtistLike);

        restArtistLikeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artistLikeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artistLikeDTO))
            )
            .andExpect(status().isOk());

        // Validate the ArtistLike in the database
        List<ArtistLike> artistLikeList = artistLikeRepository.findAll();
        assertThat(artistLikeList).hasSize(databaseSizeBeforeUpdate);
        ArtistLike testArtistLike = artistLikeList.get(artistLikeList.size() - 1);
        assertThat(testArtistLike.getVoMember()).isEqualTo(UPDATED_VO_MEMBER);
    }

    @Test
    @Transactional
    void putNonExistingArtistLike() throws Exception {
        int databaseSizeBeforeUpdate = artistLikeRepository.findAll().size();
        artistLike.setId(count.incrementAndGet());

        // Create the ArtistLike
        ArtistLikeDTO artistLikeDTO = artistLikeMapper.toDto(artistLike);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtistLikeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artistLikeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artistLikeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtistLike in the database
        List<ArtistLike> artistLikeList = artistLikeRepository.findAll();
        assertThat(artistLikeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArtistLike() throws Exception {
        int databaseSizeBeforeUpdate = artistLikeRepository.findAll().size();
        artistLike.setId(count.incrementAndGet());

        // Create the ArtistLike
        ArtistLikeDTO artistLikeDTO = artistLikeMapper.toDto(artistLike);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtistLikeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artistLikeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtistLike in the database
        List<ArtistLike> artistLikeList = artistLikeRepository.findAll();
        assertThat(artistLikeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArtistLike() throws Exception {
        int databaseSizeBeforeUpdate = artistLikeRepository.findAll().size();
        artistLike.setId(count.incrementAndGet());

        // Create the ArtistLike
        ArtistLikeDTO artistLikeDTO = artistLikeMapper.toDto(artistLike);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtistLikeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artistLikeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArtistLike in the database
        List<ArtistLike> artistLikeList = artistLikeRepository.findAll();
        assertThat(artistLikeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArtistLikeWithPatch() throws Exception {
        // Initialize the database
        artistLikeRepository.saveAndFlush(artistLike);

        int databaseSizeBeforeUpdate = artistLikeRepository.findAll().size();

        // Update the artistLike using partial update
        ArtistLike partialUpdatedArtistLike = new ArtistLike();
        partialUpdatedArtistLike.setId(artistLike.getId());

        partialUpdatedArtistLike.voMember(UPDATED_VO_MEMBER);

        restArtistLikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtistLike.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtistLike))
            )
            .andExpect(status().isOk());

        // Validate the ArtistLike in the database
        List<ArtistLike> artistLikeList = artistLikeRepository.findAll();
        assertThat(artistLikeList).hasSize(databaseSizeBeforeUpdate);
        ArtistLike testArtistLike = artistLikeList.get(artistLikeList.size() - 1);
        assertThat(testArtistLike.getVoMember()).isEqualTo(UPDATED_VO_MEMBER);
    }

    @Test
    @Transactional
    void fullUpdateArtistLikeWithPatch() throws Exception {
        // Initialize the database
        artistLikeRepository.saveAndFlush(artistLike);

        int databaseSizeBeforeUpdate = artistLikeRepository.findAll().size();

        // Update the artistLike using partial update
        ArtistLike partialUpdatedArtistLike = new ArtistLike();
        partialUpdatedArtistLike.setId(artistLike.getId());

        partialUpdatedArtistLike.voMember(UPDATED_VO_MEMBER);

        restArtistLikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtistLike.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtistLike))
            )
            .andExpect(status().isOk());

        // Validate the ArtistLike in the database
        List<ArtistLike> artistLikeList = artistLikeRepository.findAll();
        assertThat(artistLikeList).hasSize(databaseSizeBeforeUpdate);
        ArtistLike testArtistLike = artistLikeList.get(artistLikeList.size() - 1);
        assertThat(testArtistLike.getVoMember()).isEqualTo(UPDATED_VO_MEMBER);
    }

    @Test
    @Transactional
    void patchNonExistingArtistLike() throws Exception {
        int databaseSizeBeforeUpdate = artistLikeRepository.findAll().size();
        artistLike.setId(count.incrementAndGet());

        // Create the ArtistLike
        ArtistLikeDTO artistLikeDTO = artistLikeMapper.toDto(artistLike);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtistLikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, artistLikeDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artistLikeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtistLike in the database
        List<ArtistLike> artistLikeList = artistLikeRepository.findAll();
        assertThat(artistLikeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArtistLike() throws Exception {
        int databaseSizeBeforeUpdate = artistLikeRepository.findAll().size();
        artistLike.setId(count.incrementAndGet());

        // Create the ArtistLike
        ArtistLikeDTO artistLikeDTO = artistLikeMapper.toDto(artistLike);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtistLikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artistLikeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtistLike in the database
        List<ArtistLike> artistLikeList = artistLikeRepository.findAll();
        assertThat(artistLikeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArtistLike() throws Exception {
        int databaseSizeBeforeUpdate = artistLikeRepository.findAll().size();
        artistLike.setId(count.incrementAndGet());

        // Create the ArtistLike
        ArtistLikeDTO artistLikeDTO = artistLikeMapper.toDto(artistLike);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtistLikeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artistLikeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArtistLike in the database
        List<ArtistLike> artistLikeList = artistLikeRepository.findAll();
        assertThat(artistLikeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArtistLike() throws Exception {
        // Initialize the database
        artistLikeRepository.saveAndFlush(artistLike);

        int databaseSizeBeforeDelete = artistLikeRepository.findAll().size();

        // Delete the artistLike
        restArtistLikeMockMvc
            .perform(delete(ENTITY_API_URL_ID, artistLike.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArtistLike> artistLikeList = artistLikeRepository.findAll();
        assertThat(artistLikeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
