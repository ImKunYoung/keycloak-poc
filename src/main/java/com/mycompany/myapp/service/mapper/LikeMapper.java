package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Exhibition;
import com.mycompany.myapp.domain.Like;
import com.mycompany.myapp.service.dto.ExhibitionDTO;
import com.mycompany.myapp.service.dto.LikeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Like} and its DTO {@link LikeDTO}.
 */
@Mapper(componentModel = "spring")
public interface LikeMapper extends EntityMapper<LikeDTO, Like> {
    @Mapping(target = "artwork", source = "artwork", qualifiedByName = "exhibitionId")
    LikeDTO toDto(Like s);

    @Named("exhibitionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ExhibitionDTO toDtoExhibitionId(Exhibition exhibition);
}
