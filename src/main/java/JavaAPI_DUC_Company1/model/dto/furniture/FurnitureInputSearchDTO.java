package JavaAPI_DUC_Company1.model.dto.furniture;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class FurnitureInputSearchDTO {

    // Pagination
    private Integer currentPage = 1;
    private Integer pageSize = 100;

    // Search
    private String searchName;

    // Filter
    private String colour;
    private Boolean isMadeInVietnam;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer furnitureCategoryId;

    // Date range
    private LocalDate startTime;
    private LocalDate endTime;

}

