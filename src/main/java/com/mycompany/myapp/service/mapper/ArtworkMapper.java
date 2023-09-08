package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Artwork;
import com.mycompany.myapp.service.dto.ArtworkDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Artwork} and its DTO {@link ArtworkDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArtworkMapper extends EntityMapper<ArtworkDTO, Artwork> {}
