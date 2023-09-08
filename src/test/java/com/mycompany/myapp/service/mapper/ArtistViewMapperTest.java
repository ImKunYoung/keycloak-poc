package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArtistViewMapperTest {

    private ArtistViewMapper artistViewMapper;

    @BeforeEach
    public void setUp() {
        artistViewMapper = new ArtistViewMapperImpl();
    }
}
