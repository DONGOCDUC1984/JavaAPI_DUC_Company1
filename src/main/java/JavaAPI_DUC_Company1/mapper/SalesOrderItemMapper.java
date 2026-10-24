package JavaAPI_DUC_Company1.mapper;

import JavaAPI_DUC_Company1.model.SalesOrderItem;
import JavaAPI_DUC_Company1.model.dto.salesOrderItem.SalesOrderItemReadDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SalesOrderItemMapper {
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.price", target = "productPrice")
    SalesOrderItemReadDTO toReadDTO(SalesOrderItem SalesOrderItem);

    List<SalesOrderItemReadDTO> toReadDTOList(List<SalesOrderItem> SalesOrderItems);
}

