package JavaAPI_DUC_Company1.model.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductOutputSearchDTO {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer productCategoryId;
    private String productCategoryEnglishName;
    private Integer provinceCityId;
    private String provinceCityName;
    private Integer districtId;
    private String districtName;
    private String imageUrl;
    private Integer stockQuantity;

    public ProductOutputSearchDTO() {
    }
}
