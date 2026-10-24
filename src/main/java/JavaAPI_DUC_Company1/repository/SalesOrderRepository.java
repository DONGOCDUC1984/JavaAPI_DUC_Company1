package JavaAPI_DUC_Company1.repository;

import JavaAPI_DUC_Company1.model.SalesOrder;
import JavaAPI_DUC_Company1.model.SalesStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SalesOrderRepository
        extends JpaRepository<SalesOrder, Integer> {
    Optional<List<SalesOrder>> findByUserIdAndStatus(Integer userId,
                                                     SalesStatus status);
}
