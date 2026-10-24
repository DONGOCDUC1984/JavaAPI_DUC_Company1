package JavaAPI_DUC_Company1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "furnitures",
        // I can create this table first.
        // After some days or even some weeks, I can add indexes like this :
        indexes = {
                @Index(
                        name = "idx_furniture_name",
                        columnList = "name"),
                @Index(
                        name = "idx_furniture_colour",
                        columnList = "colour"),
                // isMadeInVietnam has only 2 values
                // so it is unnecessary to create its index
                @Index(
                        name = "idx_furniture_price",
                        columnList = "price"),
                @Index(
                        name = "idx_furniture_category",
                        columnList = "furniture_category_id"),
                @Index(
                        name = "idx_furniture_manufacturing_date",
                        columnList = "manufacturing_date"),
                // Composite Index since the search often combines
                // Colour,Price,FurnitureCategory,ManufacturingDate
                @Index(
                        name = "idx_furniture_search",
                        columnList = "colour,price,furniture_category_id, manufacturing_date"),

        })
@Getter
@Setter
public class Furniture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Colour is required")
    private String colour;

    @NotNull(message = "IsMadeInVietnam is required")
    private Boolean isMadeInVietnam;

    //At most 12 digits in total.2 digits after the decimal point.
    @Column(nullable = false, precision = 12, scale = 2)
    @NotNull(message = "Price is required")
    //Do not use Double here.Otherwise, for instance 0.1+0.2=0.30000000000000004
    //Banks and payment systems virtually always use BigDecimal
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( nullable = false)
    private FurnitureCategory furnitureCategory;

    @NotNull(message = "ManufacturingDate is required")
    // I should utilize LocalDate not Date here.Since
    // _Date contains Year, Month, Day, Hour, Minute, Second, Millisecond
    // _LocalDate contains only Year, Month, Day
    private LocalDate manufacturingDate;
    public Furniture() {
    }

    public Furniture( String name, String colour, Boolean isMadeInVietnam,
                      BigDecimal price, FurnitureCategory furnitureCategory,
                      LocalDate manufacturingDate) {
        this.name = name;
        this.colour = colour;
        this.isMadeInVietnam = isMadeInVietnam;
        this.price = price;
        this.furnitureCategory = furnitureCategory;
        this.manufacturingDate = manufacturingDate;
    }
}



