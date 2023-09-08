package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Artwork;
import com.mycompany.myapp.domain.ArtworkLike;
import com.mycompany.myapp.service.dto.ArtworkDTO;
import com.mycompany.myapp.service.dto.ArtworkLikeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArtworkLike} and its DTO {@link ArtworkLikeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArtworkLikeMapper extends EntityMapper<ArtworkLikeDTO, ArtworkLike> {
    @Mapping(target = "artwork", source = "artwork", qualifiedByName = "artworkId")
    ArtworkLikeDTO toDto(ArtworkLike s);

    @Named("artworkId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ArtworkDTO toDtoArtworkId(Artwork artwork);
}
