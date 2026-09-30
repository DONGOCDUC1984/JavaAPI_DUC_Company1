package JavaAPI_DUC_Company1.model.dto.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductAddUpdateDTO {
    private Integer id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "productCategoryId is required")
    private Integer productCategoryId;

    @NotNull(message = "districtId is required")
    private Integer districtId;

    //private String imageUrl;

    @NotNull(message = "Stock Quantity is required")
    @Min(value = 0)
    private Integer stockQuantity;
}
