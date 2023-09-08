package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArtworkViewMapperTest {

    private ArtworkViewMapper artworkViewMapper;

    @BeforeEach
    public void setUp() {
        artworkViewMapper = new ArtworkViewMapperImpl();
    }
}
