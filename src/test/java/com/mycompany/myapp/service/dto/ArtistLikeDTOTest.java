package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArtistLikeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArtistLikeDTO.class);
        ArtistLikeDTO artistLikeDTO1 = new ArtistLikeDTO();
        artistLikeDTO1.setId(1L);
        ArtistLikeDTO artistLikeDTO2 = new ArtistLikeDTO();
        assertThat(artistLikeDTO1).isNotEqualTo(artistLikeDTO2);
        artistLikeDTO2.setId(artistLikeDTO1.getId());
        assertThat(artistLikeDTO1).isEqualTo(artistLikeDTO2);
        artistLikeDTO2.setId(2L);
        assertThat(artistLikeDTO1).isNotEqualTo(artistLikeDTO2);
        artistLikeDTO1.setId(null);
        assertThat(artistLikeDTO1).isNotEqualTo(artistLikeDTO2);
    }
}
