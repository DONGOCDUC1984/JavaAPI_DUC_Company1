package JavaAPI_DUC_Company1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "book_categories")
@Getter
@Setter
public class BookCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name is required")
    private String name;

    public BookCategory() {
    }

    public BookCategory(String name) {
        this.name = name;
    }
}

