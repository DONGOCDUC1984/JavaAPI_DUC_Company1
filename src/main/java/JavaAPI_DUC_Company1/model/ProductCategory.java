package JavaAPI_DUC_Company1.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

//@Entity means :This class represents a table in the database
@Entity
@Table(name = "product_categories")
public class ProductCategory  {

    @Id
    //Use the database's AUTO_INCREMENT
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Here is Integer because Integer can be null.int cannot be null.
    private Integer id;

    @NotBlank(message = "English name is required")
    private String englishName;
    @NotBlank(message = "Vietnamese name is required")
    private String vietnameseName;
    // Getter & Setter

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getVietnameseName() {
        return vietnameseName;
    }

    public void setVietnameseName(String vietnameseName) {
        this.vietnameseName = vietnameseName;
    }
}