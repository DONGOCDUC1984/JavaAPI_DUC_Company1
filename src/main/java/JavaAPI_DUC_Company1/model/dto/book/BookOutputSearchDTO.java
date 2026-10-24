package JavaAPI_DUC_Company1.model.dto.book;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class BookOutputSearchDTO {
    private Integer id;
    private String name;
    private String colour;
    private Boolean isMadeInVietnam;
    private Integer bookCategoryId;
    private String bookCategoryName;
    private LocalDate publishedDay;

    public BookOutputSearchDTO() {
    }
}
