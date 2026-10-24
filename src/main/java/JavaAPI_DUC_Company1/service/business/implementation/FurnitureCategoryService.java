package JavaAPI_DUC_Company1.service.business.implementation;

import JavaAPI_DUC_Company1.model.FurnitureCategory;
import JavaAPI_DUC_Company1.repository.FurnitureCategoryRepository;
import JavaAPI_DUC_Company1.service.business.interfaces.IFurnitureCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class FurnitureCategoryService implements IFurnitureCategoryService {
    private final FurnitureCategoryRepository _repo;
    public  FurnitureCategoryService(FurnitureCategoryRepository repo){
        _repo=repo;
    }

    @Override
    public List<FurnitureCategory> getAll() {
        return _repo.findAll();
    }

    @Override
    public FurnitureCategory getById(Integer id) {
        return _repo.findById(id)
                .orElseThrow(()
                        -> new RuntimeException("FurnitureCategory not found with id " + id));
    }

    @Override
    public FurnitureCategory addUpdate(FurnitureCategory model) {
        //Add
        if (model.getId()==null || model.getId()==0){
            FurnitureCategory newModel =new FurnitureCategory();
            newModel.setName(model.getName());
            return _repo.save(newModel);
        }
        //Update
        else {
            FurnitureCategory existing=getById(model.getId());
            existing.setName(model.getName());
            return _repo.save(existing);
        }
    }
    @Override
    public void delete(Integer id) {
        _repo.deleteById(id);
    }
}

