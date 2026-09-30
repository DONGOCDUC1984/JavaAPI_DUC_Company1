package JavaAPI_DUC_Company1.repository;

import JavaAPI_DUC_Company1.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository
        extends JpaRepository<Department, Integer> {
}
