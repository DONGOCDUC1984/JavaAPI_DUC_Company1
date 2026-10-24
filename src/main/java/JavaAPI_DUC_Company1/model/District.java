package JavaAPI_DUC_Company1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "districts")
@Getter
@Setter
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name is required")
    private String name;
    //  @ManyToOne : many districts can have the same provinceCity.
    // fetch = FetchType.EAGER: Eager loading.
    // In this case:Whenever a district is loaded, load its provinceCity immediately.
    // @ManyToOne= @ManyToOne(fetch = FetchType.EAGER)
    // Because ManyToOne is already EAGER by default according to the JPA specification.
    @ManyToOne
    // The foreign key column is named provinceCity_id.
    // @JoinColumn(name = "provinceCity_id", nullable = false)
    @JoinColumn(nullable = false)
    private ProvinceCity provinceCity;

    public District() {
    }

    public District(String name ,ProvinceCity provinceCity) {
        this.name = name;
        this.provinceCity = provinceCity;
    }
}
