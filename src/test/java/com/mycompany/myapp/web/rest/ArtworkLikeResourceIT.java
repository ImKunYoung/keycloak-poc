package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ArtworkLike;
import com.mycompany.myapp.repository.ArtworkLikeRepository;
import com.mycompany.myapp.service.dto.ArtworkLikeDTO;
import com.mycompany.myapp.service.mapper.ArtworkLikeMapper;
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
 * Integration tests for the {@link ArtworkLikeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArtworkLikeResourceIT {

    private static final Long DEFAULT_MEMBER = 1L;
    private static final Long UPDATED_MEMBER = 2L;

    private static final String ENTITY_API_URL = "/api/artwork-likes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArtworkLikeRepository artworkLikeRepository;

    @Autowired
    private ArtworkLikeMapper artworkLikeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArtworkLikeMockMvc;

    private ArtworkLike artworkLike;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArtworkLike createEntity(EntityManager em) {
        ArtworkLike artworkLike = new ArtworkLike().member(DEFAULT_MEMBER);
        return artworkLike;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArtworkLike createUpdatedEntity(EntityManager em) {
        ArtworkLike artworkLike = new ArtworkLike().member(UPDATED_MEMBER);
        return artworkLike;
    }

    @BeforeEach
    public void initTest() {
        artworkLike = createEntity(em);
    }

    @Test
    @Transactional
    void createArtworkLike() throws Exception {
        int databaseSizeBeforeCreate = artworkLikeRepository.findAll().size();
        // Create the ArtworkLike
        ArtworkLikeDTO artworkLikeDTO = artworkLikeMapper.toDto(artworkLike);
        restArtworkLikeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artworkLikeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ArtworkLike in the database
        List<ArtworkLike> artworkLikeList = artworkLikeRepository.findAll();
        assertThat(artworkLikeList).hasSize(databaseSizeBeforeCreate + 1);
        ArtworkLike testArtworkLike = artworkLikeList.get(artworkLikeList.size() - 1);
        assertThat(testArtworkLike.getMember()).isEqualTo(DEFAULT_MEMBER);
    }

    @Test
    @Transactional
    void createArtworkLikeWithExistingId() throws Exception {
        // Create the ArtworkLike with an existing ID
        artworkLike.setId(1L);
        ArtworkLikeDTO artworkLikeDTO = artworkLikeMapper.toDto(artworkLike);

        int databaseSizeBeforeCreate = artworkLikeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArtworkLikeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artworkLikeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkLike in the database
        List<ArtworkLike> artworkLikeList = artworkLikeRepository.findAll();
        assertThat(artworkLikeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllArtworkLikes() throws Exception {
        // Initialize the database
        artworkLikeRepository.saveAndFlush(artworkLike);

        // Get all the artworkLikeList
        restArtworkLikeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artworkLike.getId().intValue())))
            .andExpect(jsonPath("$.[*].member").value(hasItem(DEFAULT_MEMBER.intValue())));
    }

    @Test
    @Transactional
    void getArtworkLike() throws Exception {
        // Initialize the database
        artworkLikeRepository.saveAndFlush(artworkLike);

        // Get the artworkLike
        restArtworkLikeMockMvc
            .perform(get(ENTITY_API_URL_ID, artworkLike.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(artworkLike.getId().intValue()))
            .andExpect(jsonPath("$.member").value(DEFAULT_MEMBER.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingArtworkLike() throws Exception {
        // Get the artworkLike
        restArtworkLikeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArtworkLike() throws Exception {
        // Initialize the database
        artworkLikeRepository.saveAndFlush(artworkLike);

        int databaseSizeBeforeUpdate = artworkLikeRepository.findAll().size();

        // Update the artworkLike
        ArtworkLike updatedArtworkLike = artworkLikeRepository.findById(artworkLike.getId()).get();
        // Disconnect from session so that the updates on updatedArtworkLike are not directly saved in db
        em.detach(updatedArtworkLike);
        updatedArtworkLike.member(UPDATED_MEMBER);
        ArtworkLikeDTO artworkLikeDTO = artworkLikeMapper.toDto(updatedArtworkLike);

        restArtworkLikeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artworkLikeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artworkLikeDTO))
            )
            .andExpect(status().isOk());

        // Validate the ArtworkLike in the database
        List<ArtworkLike> artworkLikeList = artworkLikeRepository.findAll();
        assertThat(artworkLikeList).hasSize(databaseSizeBeforeUpdate);
        ArtworkLike testArtworkLike = artworkLikeList.get(artworkLikeList.size() - 1);
        assertThat(testArtworkLike.getMember()).isEqualTo(UPDATED_MEMBER);
    }

    @Test
    @Transactional
    void putNonExistingArtworkLike() throws Exception {
        int databaseSizeBeforeUpdate = artworkLikeRepository.findAll().size();
        artworkLike.setId(count.incrementAndGet());

        // Create the ArtworkLike
        ArtworkLikeDTO artworkLikeDTO = artworkLikeMapper.toDto(artworkLike);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtworkLikeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artworkLikeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artworkLikeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkLike in the database
        List<ArtworkLike> artworkLikeList = artworkLikeRepository.findAll();
        assertThat(artworkLikeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArtworkLike() throws Exception {
        int databaseSizeBeforeUpdate = artworkLikeRepository.findAll().size();
        artworkLike.setId(count.incrementAndGet());

        // Create the ArtworkLike
        ArtworkLikeDTO artworkLikeDTO = artworkLikeMapper.toDto(artworkLike);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkLikeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artworkLikeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkLike in the database
        List<ArtworkLike> artworkLikeList = artworkLikeRepository.findAll();
        assertThat(artworkLikeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArtworkLike() throws Exception {
        int databaseSizeBeforeUpdate = artworkLikeRepository.findAll().size();
        artworkLike.setId(count.incrementAndGet());

        // Create the ArtworkLike
        ArtworkLikeDTO artworkLikeDTO = artworkLikeMapper.toDto(artworkLike);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkLikeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artworkLikeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArtworkLike in the database
        List<ArtworkLike> artworkLikeList = artworkLikeRepository.findAll();
        assertThat(artworkLikeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArtworkLikeWithPatch() throws Exception {
        // Initialize the database
        artworkLikeRepository.saveAndFlush(artworkLike);

        int databaseSizeBeforeUpdate = artworkLikeRepository.findAll().size();

        // Update the artworkLike using partial update
        ArtworkLike partialUpdatedArtworkLike = new ArtworkLike();
        partialUpdatedArtworkLike.setId(artworkLike.getId());

        partialUpdatedArtworkLike.member(UPDATED_MEMBER);

        restArtworkLikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtworkLike.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtworkLike))
            )
            .andExpect(status().isOk());

        // Validate the ArtworkLike in the database
        List<ArtworkLike> artworkLikeList = artworkLikeRepository.findAll();
        assertThat(artworkLikeList).hasSize(databaseSizeBeforeUpdate);
        ArtworkLike testArtworkLike = artworkLikeList.get(artworkLikeList.size() - 1);
        assertThat(testArtworkLike.getMember()).isEqualTo(UPDATED_MEMBER);
    }

    @Test
    @Transactional
    void fullUpdateArtworkLikeWithPatch() throws Exception {
        // Initialize the database
        artworkLikeRepository.saveAndFlush(artworkLike);

        int databaseSizeBeforeUpdate = artworkLikeRepository.findAll().size();

        // Update the artworkLike using partial update
        ArtworkLike partialUpdatedArtworkLike = new ArtworkLike();
        partialUpdatedArtworkLike.setId(artworkLike.getId());

        partialUpdatedArtworkLike.member(UPDATED_MEMBER);

        restArtworkLikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtworkLike.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtworkLike))
            )
            .andExpect(status().isOk());

        // Validate the ArtworkLike in the database
        List<ArtworkLike> artworkLikeList = artworkLikeRepository.findAll();
        assertThat(artworkLikeList).hasSize(databaseSizeBeforeUpdate);
        ArtworkLike testArtworkLike = artworkLikeList.get(artworkLikeList.size() - 1);
        assertThat(testArtworkLike.getMember()).isEqualTo(UPDATED_MEMBER);
    }

    @Test
    @Transactional
    void patchNonExistingArtworkLike() throws Exception {
        int databaseSizeBeforeUpdate = artworkLikeRepository.findAll().size();
        artworkLike.setId(count.incrementAndGet());

        // Create the ArtworkLike
        ArtworkLikeDTO artworkLikeDTO = artworkLikeMapper.toDto(artworkLike);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtworkLikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, artworkLikeDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artworkLikeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkLike in the database
        List<ArtworkLike> artworkLikeList = artworkLikeRepository.findAll();
        assertThat(artworkLikeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArtworkLike() throws Exception {
        int databaseSizeBeforeUpdate = artworkLikeRepository.findAll().size();
        artworkLike.setId(count.incrementAndGet());

        // Create the ArtworkLike
        ArtworkLikeDTO artworkLikeDTO = artworkLikeMapper.toDto(artworkLike);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkLikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artworkLikeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkLike in the database
        List<ArtworkLike> artworkLikeList = artworkLikeRepository.findAll();
        assertThat(artworkLikeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArtworkLike() throws Exception {
        int databaseSizeBeforeUpdate = artworkLikeRepository.findAll().size();
        artworkLike.setId(count.incrementAndGet());

        // Create the ArtworkLike
        ArtworkLikeDTO artworkLikeDTO = artworkLikeMapper.toDto(artworkLike);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkLikeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artworkLikeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArtworkLike in the database
        List<ArtworkLike> artworkLikeList = artworkLikeRepository.findAll();
        assertThat(artworkLikeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArtworkLike() throws Exception {
        // Initialize the database
        artworkLikeRepository.saveAndFlush(artworkLike);

        int databaseSizeBeforeDelete = artworkLikeRepository.findAll().size();

        // Delete the artworkLike
        restArtworkLikeMockMvc
            .perform(delete(ENTITY_API_URL_ID, artworkLike.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArtworkLike> artworkLikeList = artworkLikeRepository.findAll();
        assertThat(artworkLikeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
