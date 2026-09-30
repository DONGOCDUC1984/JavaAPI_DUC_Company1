package JavaAPI_DUC_Company1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Table(name = "books",
        // I can create this table first.
        // After some days or even some weeks, I can add indexes like this :
        indexes = {
                @Index(
                        name = "idx_book_name",
                        columnList = "name"),
                @Index(
                        name = "idx_book_colour",
                        columnList = "colour"),
                // isMadeInVietnam has only 2 values
                // so it is unnecessary to create its index
                @Index(
                        name = "idx_book_category",
                        columnList = "book_category_id"),

                @Index(
                        name = "idx_book_published_day",
                        columnList = "published_day"),
                // Composite Index since the search often combines
                // Colour,BookCategory,PublishedDay
                @Index(
                        name = "idx_book_search",
                        columnList = "colour, book_category_id, published_day"),

        })
@Getter
@Setter
public class Book {
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( nullable = false)
    private BookCategory bookCategory;

    @NotNull(message = "PublishedDay is required")
    // I should utilize LocalDate not Date here.Since
    // _Date contains Year, Month, Day, Hour, Minute, Second, Millisecond
    // _LocalDate contains only Year, Month, Day
    private LocalDate publishedDay;
    public Book() {
    }

    public Book( String name, String colour, Boolean isMadeInVietnam,
                 BookCategory bookCategory, LocalDate publishedDay) {
        this.name = name;
        this.colour = colour;
        this.isMadeInVietnam = isMadeInVietnam;
        this.bookCategory = bookCategory;
        this.publishedDay = publishedDay;
    }
}

