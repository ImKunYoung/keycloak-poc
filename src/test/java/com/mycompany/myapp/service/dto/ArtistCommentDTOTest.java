package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArtistCommentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArtistCommentDTO.class);
        ArtistCommentDTO artistCommentDTO1 = new ArtistCommentDTO();
        artistCommentDTO1.setId(1L);
        ArtistCommentDTO artistCommentDTO2 = new ArtistCommentDTO();
        assertThat(artistCommentDTO1).isNotEqualTo(artistCommentDTO2);
        artistCommentDTO2.setId(artistCommentDTO1.getId());
        assertThat(artistCommentDTO1).isEqualTo(artistCommentDTO2);
        artistCommentDTO2.setId(2L);
        assertThat(artistCommentDTO1).isNotEqualTo(artistCommentDTO2);
        artistCommentDTO1.setId(null);
        assertThat(artistCommentDTO1).isNotEqualTo(artistCommentDTO2);
    }
}
