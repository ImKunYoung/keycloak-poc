package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Artist;
import com.mycompany.myapp.service.dto.ArtistDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Artist} and its DTO {@link ArtistDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArtistMapper extends EntityMapper<ArtistDTO, Artist> {}
