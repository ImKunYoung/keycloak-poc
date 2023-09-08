package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Invoice;
import com.mycompany.myapp.service.dto.InvoiceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Invoice} and its DTO {@link InvoiceDTO}.
 */
@Mapper(componentModel = "spring")
public interface InvoiceMapper extends EntityMapper<InvoiceDTO, Invoice> {}
