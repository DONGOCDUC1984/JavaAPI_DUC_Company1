package JavaAPI_DUC_Company1.repository;

import JavaAPI_DUC_Company1.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StaffRepository
        extends JpaRepository<Staff, Integer> ,
        //JpaSpecificationExecutor is necessary for
        // _repo.search(specification, pageable); in file StaffService
        JpaSpecificationExecutor<Staff> {
    boolean existsByPosition_PositionCategoryId(Integer positionCategoryId);
}

