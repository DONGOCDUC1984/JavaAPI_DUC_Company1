package JavaAPI_DUC_Company1.model.dto.furniture;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class FurnitureOutputSearchDTO {
    private Integer id;
    private String name;
    private String colour;
    private Boolean isMadeInVietnam;
    private BigDecimal price;
    private Integer furnitureCategoryId;
    private String furnitureCategoryName;
    private LocalDate manufacturingDate;

    public FurnitureOutputSearchDTO() {
    }
}


