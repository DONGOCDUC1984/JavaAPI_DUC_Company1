package JavaAPI_DUC_Company1.model.dto.book;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class BookInputSearchDTO {

    // Pagination
    private Integer currentPage = 1;
    private Integer pageSize = 100;

    // Search
    private String searchStr;

    // Filter
    private String colour;
    private Boolean isMadeInVietnam;
    private Integer bookCategoryId;

    // Date range
    private LocalDate startTime;
    private LocalDate endTime;

}