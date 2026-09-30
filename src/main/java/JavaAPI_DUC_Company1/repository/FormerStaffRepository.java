package JavaAPI_DUC_Company1.repository;

import JavaAPI_DUC_Company1.model.FormerStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FormerStaffRepository
        extends JpaRepository<FormerStaff, Integer> ,
        //JpaSpecificationExecutor is necessary for
        // _repo.search(specification, pageable); in file FormerStaffService
        JpaSpecificationExecutor<FormerStaff> {
}


