package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArtworkLikeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArtworkLike.class);
        ArtworkLike artworkLike1 = new ArtworkLike();
        artworkLike1.setId(1L);
        ArtworkLike artworkLike2 = new ArtworkLike();
        artworkLike2.setId(artworkLike1.getId());
        assertThat(artworkLike1).isEqualTo(artworkLike2);
        artworkLike2.setId(2L);
        assertThat(artworkLike1).isNotEqualTo(artworkLike2);
        artworkLike1.setId(null);
        assertThat(artworkLike1).isNotEqualTo(artworkLike2);
    }
}
