package JavaAPI_DUC_Company1.model.dto.salesOrder;

import JavaAPI_DUC_Company1.model.SalesStatus;
import JavaAPI_DUC_Company1.model.dto.salesOrderItem.SalesOrderItemReadDTO;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SalesOrderReadDTO {
    private Integer id;
//    private Integer userId;
    private String userName;
    private LocalDateTime createdDate;
    private String userTel;
    private String userAddress;
    private BigDecimal totalCost;
    private SalesStatus status ;
    private List<SalesOrderItemReadDTO> items;
}
