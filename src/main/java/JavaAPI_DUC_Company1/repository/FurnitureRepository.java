package JavaAPI_DUC_Company1.repository;

import JavaAPI_DUC_Company1.model.Furniture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FurnitureRepository
        extends JpaRepository<Furniture, Integer> {
}
