package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.ProductOrder;
import com.mycompany.myapp.service.dto.ProductOrderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductOrder} and its DTO {@link ProductOrderDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductOrderMapper extends EntityMapper<ProductOrderDTO, ProductOrder> {}
