package JavaAPI_DUC_Company1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "furniture_categories")
@Getter
@Setter
public class FurnitureCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name is required")
    private String name;

    public FurnitureCategory() {
    }

    public FurnitureCategory(String name) {
        this.name = name;
    }
}


