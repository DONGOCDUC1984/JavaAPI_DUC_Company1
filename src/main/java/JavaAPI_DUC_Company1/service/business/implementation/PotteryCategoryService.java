package JavaAPI_DUC_Company1.service.business.implementation;

import JavaAPI_DUC_Company1.model.PotteryCategory;
import JavaAPI_DUC_Company1.repository.PotteryCategoryRepository;
import JavaAPI_DUC_Company1.service.business.interfaces.IPotteryCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class PotteryCategoryService implements IPotteryCategoryService {
    private final PotteryCategoryRepository _repo;
    public  PotteryCategoryService(PotteryCategoryRepository repo){
        _repo=repo;
    }

    @Override
    public List<PotteryCategory> getAll() {
        return _repo.findAll();
    }

    @Override
    public PotteryCategory getById(Integer id) {
        return _repo.findById(id)
                .orElseThrow(() -> new RuntimeException("PotteryCategory not found with id " + id));
    }

    @Override
    public PotteryCategory addUpdate(PotteryCategory model) {
        //Add
        if (model.getId()==null || model.getId()==0){
            PotteryCategory newModel =new PotteryCategory();
            newModel.setName(model.getName());
            return _repo.save(newModel);
        }
        //Update
        else {
            PotteryCategory existing=getById(model.getId());
            existing.setName(model.getName());
            return _repo.save(existing);
        }
    }
    @Override
    public void delete(Integer id) {
        _repo.deleteById(id);
    }
}
