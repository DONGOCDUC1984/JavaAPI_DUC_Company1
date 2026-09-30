package JavaAPI_DUC_Company1.repository;

import JavaAPI_DUC_Company1.model.Cart;
import JavaAPI_DUC_Company1.model.auth.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CartRepository
        extends JpaRepository<Cart, Integer>  {
    Optional<Cart> findByUserId(Integer userId);
}

