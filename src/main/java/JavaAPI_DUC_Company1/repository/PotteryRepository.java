package JavaAPI_DUC_Company1.repository;

import JavaAPI_DUC_Company1.model.Pottery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PotteryRepository
        extends JpaRepository<Pottery, Integer> ,
        //JpaSpecificationExecutor is necessary for
        // _repo.findAll(specification, pageable); in file PotteryService
        JpaSpecificationExecutor<Pottery> {
}

