package JavaAPI_DUC_Company1.service.business.implementation;

import JavaAPI_DUC_Company1.model.Department;
import JavaAPI_DUC_Company1.repository.DepartmentRepository;
import JavaAPI_DUC_Company1.service.business.interfaces.IDepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class DepartmentService implements IDepartmentService {
    private final DepartmentRepository _repo;
    public  DepartmentService(DepartmentRepository repo){
        _repo=repo;
    }

    @Override
    public List<Department> getAll() {
        return _repo.findAll();
    }

    @Override
    public Department getById(Integer id) {
        return _repo.findById(id)
                .orElseThrow(()
                        -> new RuntimeException("Department not found with id " + id));
    }

    @Override
    public Department addUpdate(Department model) {
        //Add
        if (model.getId()==null || model.getId()==0){
            Department newModel =new Department();
            newModel.setEnglishName(model.getEnglishName());
            newModel.setVietnameseName(model.getVietnameseName());
            return _repo.save(newModel);
        }
        //Update
        else {
            Department existing=getById(model.getId());
            existing.setEnglishName(model.getEnglishName());
            existing.setVietnameseName(model.getVietnameseName());
            return _repo.save(existing);
        }
    }

    @Override
    public void delete(Integer id) {
        _repo.deleteById(id);
    }

}

