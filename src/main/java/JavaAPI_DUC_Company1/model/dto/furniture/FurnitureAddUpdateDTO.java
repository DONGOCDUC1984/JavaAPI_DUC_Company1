package JavaAPI_DUC_Company1.model.dto.furniture;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class FurnitureAddUpdateDTO {
    private Integer id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Colour is required")
    private String colour;

    @NotNull(message = "IsMadeInVietnam is required")
    private Boolean isMadeInVietnam;

    @NotNull(message = "Price is required")
    private BigDecimal price;

    @NotNull(message = "FurnitureCategoryId is required")
    private Integer furnitureCategoryId;

    @NotNull(message = "ManufacturingDate is required")
    private LocalDate manufacturingDate;
}


