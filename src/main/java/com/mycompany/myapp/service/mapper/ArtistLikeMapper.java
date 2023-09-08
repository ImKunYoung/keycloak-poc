package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Artist;
import com.mycompany.myapp.domain.ArtistLike;
import com.mycompany.myapp.service.dto.ArtistDTO;
import com.mycompany.myapp.service.dto.ArtistLikeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArtistLike} and its DTO {@link ArtistLikeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArtistLikeMapper extends EntityMapper<ArtistLikeDTO, ArtistLike> {
    @Mapping(target = "artist", source = "artist", qualifiedByName = "artistId")
    ArtistLikeDTO toDto(ArtistLike s);

    @Named("artistId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ArtistDTO toDtoArtistId(Artist artist);
}
