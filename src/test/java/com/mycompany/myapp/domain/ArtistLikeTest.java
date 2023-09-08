package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArtistLikeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArtistLike.class);
        ArtistLike artistLike1 = new ArtistLike();
        artistLike1.setId(1L);
        ArtistLike artistLike2 = new ArtistLike();
        artistLike2.setId(artistLike1.getId());
        assertThat(artistLike1).isEqualTo(artistLike2);
        artistLike2.setId(2L);
        assertThat(artistLike1).isNotEqualTo(artistLike2);
        artistLike1.setId(null);
        assertThat(artistLike1).isNotEqualTo(artistLike2);
    }
}
