package JavaAPI_DUC_Company1.service.business.interfaces;

import JavaAPI_DUC_Company1.model.Department;
import java.util.List;

public interface IDepartmentService {
    List<Department> getAll();
    Department getById(Integer id);
    Department addUpdate(Department model);
    void delete(Integer id);
}


