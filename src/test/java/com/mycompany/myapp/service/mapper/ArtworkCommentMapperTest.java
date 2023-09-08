package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArtworkCommentMapperTest {

    private ArtworkCommentMapper artworkCommentMapper;

    @BeforeEach
    public void setUp() {
        artworkCommentMapper = new ArtworkCommentMapperImpl();
    }
}
