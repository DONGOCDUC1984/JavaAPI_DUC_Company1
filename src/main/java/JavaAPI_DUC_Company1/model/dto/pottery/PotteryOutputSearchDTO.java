package JavaAPI_DUC_Company1.model.dto.pottery;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PotteryOutputSearchDTO {
    private Integer id;
    private String name;
    private String colour;
    private Boolean isMadeInVietnam;
    private BigDecimal price;
    private Integer potteryCategoryId;
    private String potteryCategoryName;
    private LocalDate manufacturingDate;

    public PotteryOutputSearchDTO() {
    }
}

