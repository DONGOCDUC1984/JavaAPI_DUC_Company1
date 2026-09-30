package JavaAPI_DUC_Company1.model.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductInputSearchDTO {
    // Pagination
    private Integer currentPage = 1;
    private Integer pageSize = 10;

    // Search and Filter
    private String searchStr;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer productCategoryId;
    private Integer provinceCityId;
    private Integer districtId;

}
