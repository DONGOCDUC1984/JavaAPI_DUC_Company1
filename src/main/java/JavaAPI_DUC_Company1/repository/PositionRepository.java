package JavaAPI_DUC_Company1.repository;

import JavaAPI_DUC_Company1.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PositionRepository
        extends JpaRepository<Position, Integer> {
//    boolean existsByPositionCategory_Id(Integer i);
// The above method can be replaced by the following method :
    boolean existsByPositionCategoryId(Integer positionCategoryId);
//  In the above method,(Integer positionCategoryId) can be replaced by (Integer i).
//   Although (Integer i) still works,(Integer positionCategoryId) is easy to understand.

//    List<Position> findByPositionCategory_Id(Integer i);
// The above method can be replaced by 1 of the 2 following methods :
    List<Position> findByPositionCategoryId(Integer positionCategoryId);
//  In the above method,(Integer positionCategoryId) can be replaced by (Integer i).
//   Although (Integer i) still works,(Integer positionCategoryId) is easy to understand.
    //Optional<List<Position>> findByPositionCategoryId(Integer i);
}
