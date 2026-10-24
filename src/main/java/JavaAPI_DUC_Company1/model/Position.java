package JavaAPI_DUC_Company1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "positions")
@Getter
@Setter
public class Position  {
    @Id
    //Use the database's AUTO_INCREMENT
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Here is Integer because Integer can be null.int cannot be null.
    private Integer id;

    @NotBlank(message = "English name is required")
    private String englishName;
    @NotBlank(message = "Vietnamese name is required")
    private String vietnameseName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( nullable = false)
    private PositionCategory positionCategory;

    public Position() {
    }

    public Position( String englishName, String vietnameseName,
                    PositionCategory positionCategory, Department department) {
        this.englishName = englishName;
        this.vietnameseName = vietnameseName;
        this.positionCategory = positionCategory;
        this.department = department;
    }
}


