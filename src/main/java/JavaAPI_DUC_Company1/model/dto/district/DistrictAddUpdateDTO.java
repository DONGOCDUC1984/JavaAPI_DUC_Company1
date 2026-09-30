package JavaAPI_DUC_Company1.model.dto.district;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DistrictAddUpdateDTO {
    private Integer id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "provinceCityId is required")
    private Integer provinceCityId;

}
