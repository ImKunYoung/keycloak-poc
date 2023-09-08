package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArtistCommentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArtistComment.class);
        ArtistComment artistComment1 = new ArtistComment();
        artistComment1.setId(1L);
        ArtistComment artistComment2 = new ArtistComment();
        artistComment2.setId(artistComment1.getId());
        assertThat(artistComment1).isEqualTo(artistComment2);
        artistComment2.setId(2L);
        assertThat(artistComment1).isNotEqualTo(artistComment2);
        artistComment1.setId(null);
        assertThat(artistComment1).isNotEqualTo(artistComment2);
    }
}
