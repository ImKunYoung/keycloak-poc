package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArtistLikeMapperTest {

    private ArtistLikeMapper artistLikeMapper;

    @BeforeEach
    public void setUp() {
        artistLikeMapper = new ArtistLikeMapperImpl();
    }
}
