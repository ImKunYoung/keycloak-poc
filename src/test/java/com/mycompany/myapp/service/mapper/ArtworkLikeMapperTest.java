package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArtworkLikeMapperTest {

    private ArtworkLikeMapper artworkLikeMapper;

    @BeforeEach
    public void setUp() {
        artworkLikeMapper = new ArtworkLikeMapperImpl();
    }
}
