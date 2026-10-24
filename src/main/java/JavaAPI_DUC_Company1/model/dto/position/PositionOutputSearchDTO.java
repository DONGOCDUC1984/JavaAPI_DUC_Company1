package JavaAPI_DUC_Company1.model.dto.position;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PositionOutputSearchDTO {
    private Integer id;
    private String englishName;
    private String vietnameseName;
    private Integer departmentId;
    private String departmentEnglishName;
    private String departmentVietnameseName;
    private Integer positionCategoryId;
    private String positionCategoryEnglishName;
    private String positionCategoryVietnameseName;

    public PositionOutputSearchDTO() {
    }
}

