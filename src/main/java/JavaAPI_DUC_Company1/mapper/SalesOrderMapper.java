package JavaAPI_DUC_Company1.mapper;

import JavaAPI_DUC_Company1.model.SalesOrder;
import JavaAPI_DUC_Company1.model.dto.salesOrder.SalesOrderReadDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SalesOrderMapper {
    @Mapping(source = "user.username", target = "userName")
    SalesOrderReadDTO toReadDTO(SalesOrder SalesOrder);

    List<SalesOrderReadDTO> toReadDTOList(
            List<SalesOrder> SalesOrders);
}
