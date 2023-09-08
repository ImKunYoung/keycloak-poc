package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Artwork;
import com.mycompany.myapp.domain.ArtworkView;
import com.mycompany.myapp.service.dto.ArtworkDTO;
import com.mycompany.myapp.service.dto.ArtworkViewDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArtworkView} and its DTO {@link ArtworkViewDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArtworkViewMapper extends EntityMapper<ArtworkViewDTO, ArtworkView> {
    @Mapping(target = "artwork", source = "artwork", qualifiedByName = "artworkId")
    ArtworkViewDTO toDto(ArtworkView s);

    @Named("artworkId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ArtworkDTO toDtoArtworkId(Artwork artwork);
}
