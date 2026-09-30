package JavaAPI_DUC_Company1.repository;

import JavaAPI_DUC_Company1.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository
        extends JpaRepository<ProductCategory, Integer> {
}