package JavaAPI_DUC_Company1.model.dto.salesOrder;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesOrderAddUpdateDTO {
    @NotBlank(message = "User's telephone number is required")
    private String userTel;

    @NotBlank(message = "User's address is required")
    private String userAddress;

    private BigDecimal totalCost;
}
