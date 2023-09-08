package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArtistViewTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArtistView.class);
        ArtistView artistView1 = new ArtistView();
        artistView1.setId(1L);
        ArtistView artistView2 = new ArtistView();
        artistView2.setId(artistView1.getId());
        assertThat(artistView1).isEqualTo(artistView2);
        artistView2.setId(2L);
        assertThat(artistView1).isNotEqualTo(artistView2);
        artistView1.setId(null);
        assertThat(artistView1).isNotEqualTo(artistView2);
    }
}
