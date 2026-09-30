package JavaAPI_DUC_Company1.repository;

import JavaAPI_DUC_Company1.model.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCategoryRepository
        extends JpaRepository<BookCategory, Integer> {
}

