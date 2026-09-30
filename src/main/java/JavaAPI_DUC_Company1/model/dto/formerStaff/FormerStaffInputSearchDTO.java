package JavaAPI_DUC_Company1.model.dto.formerStaff;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class FormerStaffInputSearchDTO {

    // Pagination
    private Integer currentPage = 1;
    private Integer pageSize = 100;

    // Search
    private String searchStr;

    // Filter
    private String gender;
    private Integer provinceCityId;
    private Integer departmentId;
    private Integer positionId;

    // Date range
    private LocalDate startHireDate;
    private LocalDate endHireDate;

}

