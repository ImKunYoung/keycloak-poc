package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExhibitionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Exhibition.class);
        Exhibition exhibition1 = new Exhibition();
        exhibition1.setId(1L);
        Exhibition exhibition2 = new Exhibition();
        exhibition2.setId(exhibition1.getId());
        assertThat(exhibition1).isEqualTo(exhibition2);
        exhibition2.setId(2L);
        assertThat(exhibition1).isNotEqualTo(exhibition2);
        exhibition1.setId(null);
        assertThat(exhibition1).isNotEqualTo(exhibition2);
    }
}
