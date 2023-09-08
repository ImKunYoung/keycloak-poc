package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArtistViewDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArtistViewDTO.class);
        ArtistViewDTO artistViewDTO1 = new ArtistViewDTO();
        artistViewDTO1.setId(1L);
        ArtistViewDTO artistViewDTO2 = new ArtistViewDTO();
        assertThat(artistViewDTO1).isNotEqualTo(artistViewDTO2);
        artistViewDTO2.setId(artistViewDTO1.getId());
        assertThat(artistViewDTO1).isEqualTo(artistViewDTO2);
        artistViewDTO2.setId(2L);
        assertThat(artistViewDTO1).isNotEqualTo(artistViewDTO2);
        artistViewDTO1.setId(null);
        assertThat(artistViewDTO1).isNotEqualTo(artistViewDTO2);
    }
}
