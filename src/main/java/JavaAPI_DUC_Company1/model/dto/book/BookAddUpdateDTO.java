package JavaAPI_DUC_Company1.model.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookAddUpdateDTO {
    private Integer id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Colour is required")
    private String colour;

    @NotNull(message = "IsMadeInVietnam is required")
    private Boolean isMadeInVietnam;

    @NotNull(message = "bookCategoryId is required")
    private Integer bookCategoryId;

    @NotNull(message = "PublishedDay is required")
    private LocalDate publishedDay;
}
