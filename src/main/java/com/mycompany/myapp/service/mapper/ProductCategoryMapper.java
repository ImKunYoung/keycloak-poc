package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.ProductCategory;
import com.mycompany.myapp.service.dto.ProductCategoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductCategory} and its DTO {@link ProductCategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductCategoryMapper extends EntityMapper<ProductCategoryDTO, ProductCategory> {}
