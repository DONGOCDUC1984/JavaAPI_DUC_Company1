package JavaAPI_DUC_Company1.repository;

import JavaAPI_DUC_Company1.model.CartItem;
import JavaAPI_DUC_Company1.model.SalesOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SalesOrderItemRepository
        extends JpaRepository<SalesOrderItem, Integer> {
    Optional<List<SalesOrderItem>> findBySalesOrderId(Integer salesOrderId);
}
