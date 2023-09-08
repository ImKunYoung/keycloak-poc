package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.OrderItem;
import com.mycompany.myapp.domain.Product;
import com.mycompany.myapp.domain.ProductOrder;
import com.mycompany.myapp.service.dto.OrderItemDTO;
import com.mycompany.myapp.service.dto.ProductDTO;
import com.mycompany.myapp.service.dto.ProductOrderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderItem} and its DTO {@link OrderItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderItemMapper extends EntityMapper<OrderItemDTO, OrderItem> {
    @Mapping(target = "product", source = "product", qualifiedByName = "productName")
    @Mapping(target = "order", source = "order", qualifiedByName = "productOrderCode")
    OrderItemDTO toDto(OrderItem s);

    @Named("productName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ProductDTO toDtoProductName(Product product);

    @Named("productOrderCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    ProductOrderDTO toDtoProductOrderCode(ProductOrder productOrder);
}
