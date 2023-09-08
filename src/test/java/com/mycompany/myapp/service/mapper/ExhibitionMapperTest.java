package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExhibitionMapperTest {

    private ExhibitionMapper exhibitionMapper;

    @BeforeEach
    public void setUp() {
        exhibitionMapper = new ExhibitionMapperImpl();
    }
}
