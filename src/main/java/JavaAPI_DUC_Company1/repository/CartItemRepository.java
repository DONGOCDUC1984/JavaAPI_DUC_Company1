package JavaAPI_DUC_Company1.repository;

import JavaAPI_DUC_Company1.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CartItemRepository
        extends JpaRepository<CartItem, Integer>  {
    Optional<List<CartItem>> findByCartId(Integer cartId);
    Optional<CartItem> findByCartIdAndProductId(Integer cartId,Integer productId);
}

