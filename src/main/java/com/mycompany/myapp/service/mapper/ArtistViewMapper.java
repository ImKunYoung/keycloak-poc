package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Artist;
import com.mycompany.myapp.domain.ArtistView;
import com.mycompany.myapp.service.dto.ArtistDTO;
import com.mycompany.myapp.service.dto.ArtistViewDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArtistView} and its DTO {@link ArtistViewDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArtistViewMapper extends EntityMapper<ArtistViewDTO, ArtistView> {
    @Mapping(target = "artist", source = "artist", qualifiedByName = "artistId")
    ArtistViewDTO toDto(ArtistView s);

    @Named("artistId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ArtistDTO toDtoArtistId(Artist artist);
}
