package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ArtistComment;
import com.mycompany.myapp.repository.ArtistCommentRepository;
import com.mycompany.myapp.service.dto.ArtistCommentDTO;
import com.mycompany.myapp.service.mapper.ArtistCommentMapper;
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
 * Integration tests for the {@link ArtistCommentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArtistCommentResourceIT {

    private static final Long DEFAULT_VO_MEMBER = 1L;
    private static final Long UPDATED_VO_MEMBER = 2L;

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/artist-comments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArtistCommentRepository artistCommentRepository;

    @Autowired
    private ArtistCommentMapper artistCommentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArtistCommentMockMvc;

    private ArtistComment artistComment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArtistComment createEntity(EntityManager em) {
        ArtistComment artistComment = new ArtistComment().voMember(DEFAULT_VO_MEMBER).content(DEFAULT_CONTENT);
        return artistComment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArtistComment createUpdatedEntity(EntityManager em) {
        ArtistComment artistComment = new ArtistComment().voMember(UPDATED_VO_MEMBER).content(UPDATED_CONTENT);
        return artistComment;
    }

    @BeforeEach
    public void initTest() {
        artistComment = createEntity(em);
    }

    @Test
    @Transactional
    void createArtistComment() throws Exception {
        int databaseSizeBeforeCreate = artistCommentRepository.findAll().size();
        // Create the ArtistComment
        ArtistCommentDTO artistCommentDTO = artistCommentMapper.toDto(artistComment);
        restArtistCommentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artistCommentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ArtistComment in the database
        List<ArtistComment> artistCommentList = artistCommentRepository.findAll();
        assertThat(artistCommentList).hasSize(databaseSizeBeforeCreate + 1);
        ArtistComment testArtistComment = artistCommentList.get(artistCommentList.size() - 1);
        assertThat(testArtistComment.getVoMember()).isEqualTo(DEFAULT_VO_MEMBER);
        assertThat(testArtistComment.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    void createArtistCommentWithExistingId() throws Exception {
        // Create the ArtistComment with an existing ID
        artistComment.setId(1L);
        ArtistCommentDTO artistCommentDTO = artistCommentMapper.toDto(artistComment);

        int databaseSizeBeforeCreate = artistCommentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArtistCommentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artistCommentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtistComment in the database
        List<ArtistComment> artistCommentList = artistCommentRepository.findAll();
        assertThat(artistCommentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllArtistComments() throws Exception {
        // Initialize the database
        artistCommentRepository.saveAndFlush(artistComment);

        // Get all the artistCommentList
        restArtistCommentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artistComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].voMember").value(hasItem(DEFAULT_VO_MEMBER.intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }

    @Test
    @Transactional
    void getArtistComment() throws Exception {
        // Initialize the database
        artistCommentRepository.saveAndFlush(artistComment);

        // Get the artistComment
        restArtistCommentMockMvc
            .perform(get(ENTITY_API_URL_ID, artistComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(artistComment.getId().intValue()))
            .andExpect(jsonPath("$.voMember").value(DEFAULT_VO_MEMBER.intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    @Transactional
    void getNonExistingArtistComment() throws Exception {
        // Get the artistComment
        restArtistCommentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArtistComment() throws Exception {
        // Initialize the database
        artistCommentRepository.saveAndFlush(artistComment);

        int databaseSizeBeforeUpdate = artistCommentRepository.findAll().size();

        // Update the artistComment
        ArtistComment updatedArtistComment = artistCommentRepository.findById(artistComment.getId()).get();
        // Disconnect from session so that the updates on updatedArtistComment are not directly saved in db
        em.detach(updatedArtistComment);
        updatedArtistComment.voMember(UPDATED_VO_MEMBER).content(UPDATED_CONTENT);
        ArtistCommentDTO artistCommentDTO = artistCommentMapper.toDto(updatedArtistComment);

        restArtistCommentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artistCommentDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artistCommentDTO))
            )
            .andExpect(status().isOk());

        // Validate the ArtistComment in the database
        List<ArtistComment> artistCommentList = artistCommentRepository.findAll();
        assertThat(artistCommentList).hasSize(databaseSizeBeforeUpdate);
        ArtistComment testArtistComment = artistCommentList.get(artistCommentList.size() - 1);
        assertThat(testArtistComment.getVoMember()).isEqualTo(UPDATED_VO_MEMBER);
        assertThat(testArtistComment.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void putNonExistingArtistComment() throws Exception {
        int databaseSizeBeforeUpdate = artistCommentRepository.findAll().size();
        artistComment.setId(count.incrementAndGet());

        // Create the ArtistComment
        ArtistCommentDTO artistCommentDTO = artistCommentMapper.toDto(artistComment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtistCommentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artistCommentDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artistCommentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtistComment in the database
        List<ArtistComment> artistCommentList = artistCommentRepository.findAll();
        assertThat(artistCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArtistComment() throws Exception {
        int databaseSizeBeforeUpdate = artistCommentRepository.findAll().size();
        artistComment.setId(count.incrementAndGet());

        // Create the ArtistComment
        ArtistCommentDTO artistCommentDTO = artistCommentMapper.toDto(artistComment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtistCommentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artistCommentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtistComment in the database
        List<ArtistComment> artistCommentList = artistCommentRepository.findAll();
        assertThat(artistCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArtistComment() throws Exception {
        int databaseSizeBeforeUpdate = artistCommentRepository.findAll().size();
        artistComment.setId(count.incrementAndGet());

        // Create the ArtistComment
        ArtistCommentDTO artistCommentDTO = artistCommentMapper.toDto(artistComment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtistCommentMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artistCommentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArtistComment in the database
        List<ArtistComment> artistCommentList = artistCommentRepository.findAll();
        assertThat(artistCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArtistCommentWithPatch() throws Exception {
        // Initialize the database
        artistCommentRepository.saveAndFlush(artistComment);

        int databaseSizeBeforeUpdate = artistCommentRepository.findAll().size();

        // Update the artistComment using partial update
        ArtistComment partialUpdatedArtistComment = new ArtistComment();
        partialUpdatedArtistComment.setId(artistComment.getId());

        restArtistCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtistComment.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtistComment))
            )
            .andExpect(status().isOk());

        // Validate the ArtistComment in the database
        List<ArtistComment> artistCommentList = artistCommentRepository.findAll();
        assertThat(artistCommentList).hasSize(databaseSizeBeforeUpdate);
        ArtistComment testArtistComment = artistCommentList.get(artistCommentList.size() - 1);
        assertThat(testArtistComment.getVoMember()).isEqualTo(DEFAULT_VO_MEMBER);
        assertThat(testArtistComment.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    void fullUpdateArtistCommentWithPatch() throws Exception {
        // Initialize the database
        artistCommentRepository.saveAndFlush(artistComment);

        int databaseSizeBeforeUpdate = artistCommentRepository.findAll().size();

        // Update the artistComment using partial update
        ArtistComment partialUpdatedArtistComment = new ArtistComment();
        partialUpdatedArtistComment.setId(artistComment.getId());

        partialUpdatedArtistComment.voMember(UPDATED_VO_MEMBER).content(UPDATED_CONTENT);

        restArtistCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtistComment.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtistComment))
            )
            .andExpect(status().isOk());

        // Validate the ArtistComment in the database
        List<ArtistComment> artistCommentList = artistCommentRepository.findAll();
        assertThat(artistCommentList).hasSize(databaseSizeBeforeUpdate);
        ArtistComment testArtistComment = artistCommentList.get(artistCommentList.size() - 1);
        assertThat(testArtistComment.getVoMember()).isEqualTo(UPDATED_VO_MEMBER);
        assertThat(testArtistComment.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void patchNonExistingArtistComment() throws Exception {
        int databaseSizeBeforeUpdate = artistCommentRepository.findAll().size();
        artistComment.setId(count.incrementAndGet());

        // Create the ArtistComment
        ArtistCommentDTO artistCommentDTO = artistCommentMapper.toDto(artistComment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtistCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, artistCommentDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artistCommentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtistComment in the database
        List<ArtistComment> artistCommentList = artistCommentRepository.findAll();
        assertThat(artistCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArtistComment() throws Exception {
        int databaseSizeBeforeUpdate = artistCommentRepository.findAll().size();
        artistComment.setId(count.incrementAndGet());

        // Create the ArtistComment
        ArtistCommentDTO artistCommentDTO = artistCommentMapper.toDto(artistComment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtistCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artistCommentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtistComment in the database
        List<ArtistComment> artistCommentList = artistCommentRepository.findAll();
        assertThat(artistCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArtistComment() throws Exception {
        int databaseSizeBeforeUpdate = artistCommentRepository.findAll().size();
        artistComment.setId(count.incrementAndGet());

        // Create the ArtistComment
        ArtistCommentDTO artistCommentDTO = artistCommentMapper.toDto(artistComment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtistCommentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artistCommentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArtistComment in the database
        List<ArtistComment> artistCommentList = artistCommentRepository.findAll();
        assertThat(artistCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArtistComment() throws Exception {
        // Initialize the database
        artistCommentRepository.saveAndFlush(artistComment);

        int databaseSizeBeforeDelete = artistCommentRepository.findAll().size();

        // Delete the artistComment
        restArtistCommentMockMvc
            .perform(delete(ENTITY_API_URL_ID, artistComment.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArtistComment> artistCommentList = artistCommentRepository.findAll();
        assertThat(artistCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
