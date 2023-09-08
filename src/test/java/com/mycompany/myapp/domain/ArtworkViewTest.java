package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArtworkViewTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArtworkView.class);
        ArtworkView artworkView1 = new ArtworkView();
        artworkView1.setId(1L);
        ArtworkView artworkView2 = new ArtworkView();
        artworkView2.setId(artworkView1.getId());
        assertThat(artworkView1).isEqualTo(artworkView2);
        artworkView2.setId(2L);
        assertThat(artworkView1).isNotEqualTo(artworkView2);
        artworkView1.setId(null);
        assertThat(artworkView1).isNotEqualTo(artworkView2);
    }
}
