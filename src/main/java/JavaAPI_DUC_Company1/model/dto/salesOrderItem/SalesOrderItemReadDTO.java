package JavaAPI_DUC_Company1.model.dto.salesOrderItem;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class SalesOrderItemReadDTO {
//    private Integer id;
//    private Integer salesOrderId;
    private Integer productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer quantity;
//    private SalesStatus status ;
}
