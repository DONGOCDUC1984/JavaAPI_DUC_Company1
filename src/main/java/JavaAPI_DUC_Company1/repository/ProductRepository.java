package JavaAPI_DUC_Company1.repository;

import JavaAPI_DUC_Company1.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository
        extends JpaRepository<Product, Integer> ,
        //JpaSpecificationExecutor is necessary for
        // _repo.search(specification, pageable); in file ProductService
        JpaSpecificationExecutor<Product> {
}
