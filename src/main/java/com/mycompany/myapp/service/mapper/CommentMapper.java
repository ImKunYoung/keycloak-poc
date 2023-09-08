package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Comment;
import com.mycompany.myapp.domain.Exhibition;
import com.mycompany.myapp.service.dto.CommentDTO;
import com.mycompany.myapp.service.dto.ExhibitionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comment} and its DTO {@link CommentDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {
    @Mapping(target = "artwork", source = "artwork", qualifiedByName = "exhibitionId")
    CommentDTO toDto(Comment s);

    @Named("exhibitionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ExhibitionDTO toDtoExhibitionId(Exhibition exhibition);
}
