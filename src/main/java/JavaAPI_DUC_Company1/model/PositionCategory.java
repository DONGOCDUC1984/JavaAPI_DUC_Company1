package JavaAPI_DUC_Company1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

//@Entity means :This class represents a table in the database
@Entity
@Table(name = "position_categories")
@Getter
@Setter
public class PositionCategory  {
    @Id
    //Use the database's AUTO_INCREMENT
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Here is Integer because Integer can be null.int cannot be null.
    private Integer id;

    @NotBlank(message = "English name is required")
    private String englishName;
    @NotBlank(message = "Vietnamese name is required")
    private String vietnameseName;

    public PositionCategory() {
    }

    public PositionCategory(String englishName, String vietnameseName) {
        this.englishName = englishName;
        this.vietnameseName = vietnameseName;
    }
}

