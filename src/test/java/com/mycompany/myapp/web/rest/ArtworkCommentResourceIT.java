package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ArtworkComment;
import com.mycompany.myapp.repository.ArtworkCommentRepository;
import com.mycompany.myapp.service.dto.ArtworkCommentDTO;
import com.mycompany.myapp.service.mapper.ArtworkCommentMapper;
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
 * Integration tests for the {@link ArtworkCommentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArtworkCommentResourceIT {

    private static final Long DEFAULT_MEMBER = 1L;
    private static final Long UPDATED_MEMBER = 2L;

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/artwork-comments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArtworkCommentRepository artworkCommentRepository;

    @Autowired
    private ArtworkCommentMapper artworkCommentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArtworkCommentMockMvc;

    private ArtworkComment artworkComment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArtworkComment createEntity(EntityManager em) {
        ArtworkComment artworkComment = new ArtworkComment().member(DEFAULT_MEMBER).content(DEFAULT_CONTENT);
        return artworkComment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArtworkComment createUpdatedEntity(EntityManager em) {
        ArtworkComment artworkComment = new ArtworkComment().member(UPDATED_MEMBER).content(UPDATED_CONTENT);
        return artworkComment;
    }

    @BeforeEach
    public void initTest() {
        artworkComment = createEntity(em);
    }

    @Test
    @Transactional
    void createArtworkComment() throws Exception {
        int databaseSizeBeforeCreate = artworkCommentRepository.findAll().size();
        // Create the ArtworkComment
        ArtworkCommentDTO artworkCommentDTO = artworkCommentMapper.toDto(artworkComment);
        restArtworkCommentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artworkCommentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ArtworkComment in the database
        List<ArtworkComment> artworkCommentList = artworkCommentRepository.findAll();
        assertThat(artworkCommentList).hasSize(databaseSizeBeforeCreate + 1);
        ArtworkComment testArtworkComment = artworkCommentList.get(artworkCommentList.size() - 1);
        assertThat(testArtworkComment.getMember()).isEqualTo(DEFAULT_MEMBER);
        assertThat(testArtworkComment.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    void createArtworkCommentWithExistingId() throws Exception {
        // Create the ArtworkComment with an existing ID
        artworkComment.setId(1L);
        ArtworkCommentDTO artworkCommentDTO = artworkCommentMapper.toDto(artworkComment);

        int databaseSizeBeforeCreate = artworkCommentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArtworkCommentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artworkCommentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkComment in the database
        List<ArtworkComment> artworkCommentList = artworkCommentRepository.findAll();
        assertThat(artworkCommentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllArtworkComments() throws Exception {
        // Initialize the database
        artworkCommentRepository.saveAndFlush(artworkComment);

        // Get all the artworkCommentList
        restArtworkCommentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artworkComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].member").value(hasItem(DEFAULT_MEMBER.intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }

    @Test
    @Transactional
    void getArtworkComment() throws Exception {
        // Initialize the database
        artworkCommentRepository.saveAndFlush(artworkComment);

        // Get the artworkComment
        restArtworkCommentMockMvc
            .perform(get(ENTITY_API_URL_ID, artworkComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(artworkComment.getId().intValue()))
            .andExpect(jsonPath("$.member").value(DEFAULT_MEMBER.intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    @Transactional
    void getNonExistingArtworkComment() throws Exception {
        // Get the artworkComment
        restArtworkCommentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArtworkComment() throws Exception {
        // Initialize the database
        artworkCommentRepository.saveAndFlush(artworkComment);

        int databaseSizeBeforeUpdate = artworkCommentRepository.findAll().size();

        // Update the artworkComment
        ArtworkComment updatedArtworkComment = artworkCommentRepository.findById(artworkComment.getId()).get();
        // Disconnect from session so that the updates on updatedArtworkComment are not directly saved in db
        em.detach(updatedArtworkComment);
        updatedArtworkComment.member(UPDATED_MEMBER).content(UPDATED_CONTENT);
        ArtworkCommentDTO artworkCommentDTO = artworkCommentMapper.toDto(updatedArtworkComment);

        restArtworkCommentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artworkCommentDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artworkCommentDTO))
            )
            .andExpect(status().isOk());

        // Validate the ArtworkComment in the database
        List<ArtworkComment> artworkCommentList = artworkCommentRepository.findAll();
        assertThat(artworkCommentList).hasSize(databaseSizeBeforeUpdate);
        ArtworkComment testArtworkComment = artworkCommentList.get(artworkCommentList.size() - 1);
        assertThat(testArtworkComment.getMember()).isEqualTo(UPDATED_MEMBER);
        assertThat(testArtworkComment.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void putNonExistingArtworkComment() throws Exception {
        int databaseSizeBeforeUpdate = artworkCommentRepository.findAll().size();
        artworkComment.setId(count.incrementAndGet());

        // Create the ArtworkComment
        ArtworkCommentDTO artworkCommentDTO = artworkCommentMapper.toDto(artworkComment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtworkCommentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artworkCommentDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artworkCommentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkComment in the database
        List<ArtworkComment> artworkCommentList = artworkCommentRepository.findAll();
        assertThat(artworkCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArtworkComment() throws Exception {
        int databaseSizeBeforeUpdate = artworkCommentRepository.findAll().size();
        artworkComment.setId(count.incrementAndGet());

        // Create the ArtworkComment
        ArtworkCommentDTO artworkCommentDTO = artworkCommentMapper.toDto(artworkComment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkCommentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artworkCommentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkComment in the database
        List<ArtworkComment> artworkCommentList = artworkCommentRepository.findAll();
        assertThat(artworkCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArtworkComment() throws Exception {
        int databaseSizeBeforeUpdate = artworkCommentRepository.findAll().size();
        artworkComment.setId(count.incrementAndGet());

        // Create the ArtworkComment
        ArtworkCommentDTO artworkCommentDTO = artworkCommentMapper.toDto(artworkComment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkCommentMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artworkCommentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArtworkComment in the database
        List<ArtworkComment> artworkCommentList = artworkCommentRepository.findAll();
        assertThat(artworkCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArtworkCommentWithPatch() throws Exception {
        // Initialize the database
        artworkCommentRepository.saveAndFlush(artworkComment);

        int databaseSizeBeforeUpdate = artworkCommentRepository.findAll().size();

        // Update the artworkComment using partial update
        ArtworkComment partialUpdatedArtworkComment = new ArtworkComment();
        partialUpdatedArtworkComment.setId(artworkComment.getId());

        partialUpdatedArtworkComment.member(UPDATED_MEMBER).content(UPDATED_CONTENT);

        restArtworkCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtworkComment.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtworkComment))
            )
            .andExpect(status().isOk());

        // Validate the ArtworkComment in the database
        List<ArtworkComment> artworkCommentList = artworkCommentRepository.findAll();
        assertThat(artworkCommentList).hasSize(databaseSizeBeforeUpdate);
        ArtworkComment testArtworkComment = artworkCommentList.get(artworkCommentList.size() - 1);
        assertThat(testArtworkComment.getMember()).isEqualTo(UPDATED_MEMBER);
        assertThat(testArtworkComment.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void fullUpdateArtworkCommentWithPatch() throws Exception {
        // Initialize the database
        artworkCommentRepository.saveAndFlush(artworkComment);

        int databaseSizeBeforeUpdate = artworkCommentRepository.findAll().size();

        // Update the artworkComment using partial update
        ArtworkComment partialUpdatedArtworkComment = new ArtworkComment();
        partialUpdatedArtworkComment.setId(artworkComment.getId());

        partialUpdatedArtworkComment.member(UPDATED_MEMBER).content(UPDATED_CONTENT);

        restArtworkCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtworkComment.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtworkComment))
            )
            .andExpect(status().isOk());

        // Validate the ArtworkComment in the database
        List<ArtworkComment> artworkCommentList = artworkCommentRepository.findAll();
        assertThat(artworkCommentList).hasSize(databaseSizeBeforeUpdate);
        ArtworkComment testArtworkComment = artworkCommentList.get(artworkCommentList.size() - 1);
        assertThat(testArtworkComment.getMember()).isEqualTo(UPDATED_MEMBER);
        assertThat(testArtworkComment.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void patchNonExistingArtworkComment() throws Exception {
        int databaseSizeBeforeUpdate = artworkCommentRepository.findAll().size();
        artworkComment.setId(count.incrementAndGet());

        // Create the ArtworkComment
        ArtworkCommentDTO artworkCommentDTO = artworkCommentMapper.toDto(artworkComment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtworkCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, artworkCommentDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artworkCommentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkComment in the database
        List<ArtworkComment> artworkCommentList = artworkCommentRepository.findAll();
        assertThat(artworkCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArtworkComment() throws Exception {
        int databaseSizeBeforeUpdate = artworkCommentRepository.findAll().size();
        artworkComment.setId(count.incrementAndGet());

        // Create the ArtworkComment
        ArtworkCommentDTO artworkCommentDTO = artworkCommentMapper.toDto(artworkComment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artworkCommentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkComment in the database
        List<ArtworkComment> artworkCommentList = artworkCommentRepository.findAll();
        assertThat(artworkCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArtworkComment() throws Exception {
        int databaseSizeBeforeUpdate = artworkCommentRepository.findAll().size();
        artworkComment.setId(count.incrementAndGet());

        // Create the ArtworkComment
        ArtworkCommentDTO artworkCommentDTO = artworkCommentMapper.toDto(artworkComment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkCommentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artworkCommentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArtworkComment in the database
        List<ArtworkComment> artworkCommentList = artworkCommentRepository.findAll();
        assertThat(artworkCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArtworkComment() throws Exception {
        // Initialize the database
        artworkCommentRepository.saveAndFlush(artworkComment);

        int databaseSizeBeforeDelete = artworkCommentRepository.findAll().size();

        // Delete the artworkComment
        restArtworkCommentMockMvc
            .perform(delete(ENTITY_API_URL_ID, artworkComment.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArtworkComment> artworkCommentList = artworkCommentRepository.findAll();
        assertThat(artworkCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
