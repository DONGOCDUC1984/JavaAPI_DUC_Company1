package JavaAPI_DUC_Company1.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "Name is required")
    private String name;

    @Column(length = 1000)
    @NotBlank(message = "Description is required")
    private String description;
    //At most 12 digits in total.2 digits after the decimal point.
    @Column(nullable = false, precision = 12, scale = 2)
    @NotNull(message = "Price is required")
    //Do not use Double here.Otherwise, for instance 0.1+0.2=0.30000000000000004
    //Banks and payment systems virtually always use BigDecimal
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ProductCategory productCategory;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ProvinceCity provinceCity;

    @ManyToOne
    @JoinColumn(nullable = false)
    private District district;

    @Column(length = 500)
    private String imageUrl;

    @Column(nullable = false)
    @Min(value = 0)
    @NotNull(message = "Stock Quantity is required")
    private Integer stockQuantity;

    public Product() {
    }

    public Product( String name, String description, BigDecimal price,
                    ProductCategory productCategory, ProvinceCity provinceCity,
                    District district, String imageUrl, Integer stockQuantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.productCategory = productCategory;
        this.provinceCity = provinceCity;
        this.district = district;
        this.imageUrl = imageUrl;
        this.stockQuantity = stockQuantity;
    }

}
