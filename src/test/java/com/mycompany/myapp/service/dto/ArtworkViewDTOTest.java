package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArtworkViewDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArtworkViewDTO.class);
        ArtworkViewDTO artworkViewDTO1 = new ArtworkViewDTO();
        artworkViewDTO1.setId(1L);
        ArtworkViewDTO artworkViewDTO2 = new ArtworkViewDTO();
        assertThat(artworkViewDTO1).isNotEqualTo(artworkViewDTO2);
        artworkViewDTO2.setId(artworkViewDTO1.getId());
        assertThat(artworkViewDTO1).isEqualTo(artworkViewDTO2);
        artworkViewDTO2.setId(2L);
        assertThat(artworkViewDTO1).isNotEqualTo(artworkViewDTO2);
        artworkViewDTO1.setId(null);
        assertThat(artworkViewDTO1).isNotEqualTo(artworkViewDTO2);
    }
}
