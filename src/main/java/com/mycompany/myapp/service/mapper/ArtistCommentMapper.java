package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Artist;
import com.mycompany.myapp.domain.ArtistComment;
import com.mycompany.myapp.service.dto.ArtistCommentDTO;
import com.mycompany.myapp.service.dto.ArtistDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArtistComment} and its DTO {@link ArtistCommentDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArtistCommentMapper extends EntityMapper<ArtistCommentDTO, ArtistComment> {
    @Mapping(target = "artist", source = "artist", qualifiedByName = "artistId")
    ArtistCommentDTO toDto(ArtistComment s);

    @Named("artistId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ArtistDTO toDtoArtistId(Artist artist);
}
