package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Exhibition;
import com.mycompany.myapp.service.dto.ExhibitionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Exhibition} and its DTO {@link ExhibitionDTO}.
 */
@Mapper(componentModel = "spring")
public interface ExhibitionMapper extends EntityMapper<ExhibitionDTO, Exhibition> {}
