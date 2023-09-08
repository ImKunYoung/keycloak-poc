package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Exhibition;
import com.mycompany.myapp.domain.View;
import com.mycompany.myapp.service.dto.ExhibitionDTO;
import com.mycompany.myapp.service.dto.ViewDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link View} and its DTO {@link ViewDTO}.
 */
@Mapper(componentModel = "spring")
public interface ViewMapper extends EntityMapper<ViewDTO, View> {
    @Mapping(target = "artwork", source = "artwork", qualifiedByName = "exhibitionId")
    ViewDTO toDto(View s);

    @Named("exhibitionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ExhibitionDTO toDtoExhibitionId(Exhibition exhibition);
}
