package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArtworkCommentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArtworkComment.class);
        ArtworkComment artworkComment1 = new ArtworkComment();
        artworkComment1.setId(1L);
        ArtworkComment artworkComment2 = new ArtworkComment();
        artworkComment2.setId(artworkComment1.getId());
        assertThat(artworkComment1).isEqualTo(artworkComment2);
        artworkComment2.setId(2L);
        assertThat(artworkComment1).isNotEqualTo(artworkComment2);
        artworkComment1.setId(null);
        assertThat(artworkComment1).isNotEqualTo(artworkComment2);
    }
}
