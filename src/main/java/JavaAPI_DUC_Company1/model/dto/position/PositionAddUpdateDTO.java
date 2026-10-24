package JavaAPI_DUC_Company1.model.dto.position;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PositionAddUpdateDTO {
    private Integer id;

    @NotBlank(message = "EnglishName is required")
    private String englishName;

    @NotBlank(message = "VietnameseName is required")
    private String vietnameseName;

    @NotNull(message = "departmentId is required")
    private Integer departmentId;

    @NotNull(message = "positionCategoryId is required")
    private Integer positionCategoryId;

    public PositionAddUpdateDTO() {
    }

    public PositionAddUpdateDTO(String englishName, String vietnameseName,
                                Integer departmentId, Integer positionCategoryId) {
        this.englishName = englishName;
        this.vietnameseName = vietnameseName;
        this.departmentId = departmentId;
        this.positionCategoryId = positionCategoryId;
    }
}

