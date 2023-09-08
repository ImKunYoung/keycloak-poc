package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Artwork;
import com.mycompany.myapp.domain.ArtworkComment;
import com.mycompany.myapp.service.dto.ArtworkCommentDTO;
import com.mycompany.myapp.service.dto.ArtworkDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArtworkComment} and its DTO {@link ArtworkCommentDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArtworkCommentMapper extends EntityMapper<ArtworkCommentDTO, ArtworkComment> {
    @Mapping(target = "artwork", source = "artwork", qualifiedByName = "artworkId")
    ArtworkCommentDTO toDto(ArtworkComment s);

    @Named("artworkId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ArtworkDTO toDtoArtworkId(Artwork artwork);
}
