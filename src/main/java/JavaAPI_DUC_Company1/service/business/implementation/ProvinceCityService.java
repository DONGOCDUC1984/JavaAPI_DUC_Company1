package JavaAPI_DUC_Company1.service.business.implementation;

import JavaAPI_DUC_Company1.model.ProvinceCity;
import JavaAPI_DUC_Company1.repository.ProvinceCityRepository;
import JavaAPI_DUC_Company1.service.business.interfaces.IProvinceCityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProvinceCityService implements IProvinceCityService {
    private final ProvinceCityRepository _repo;
    public  ProvinceCityService(ProvinceCityRepository repo){
        _repo=repo;
    }

    @Override
    public List<ProvinceCity> getAll() {
        return _repo.findAll();
    }

    @Override
    public ProvinceCity getById(Integer id) {
        return _repo.findById(id)
                .orElseThrow(()->new RuntimeException("ProvinceCity not found with id " + id) )
                ;
    }

    @Override
    public ProvinceCity addUpdate(ProvinceCity model) {
        //Add
        if (model.getId()==null || model.getId()==0){
            ProvinceCity newModel =new ProvinceCity();
            newModel.setName(model.getName());
            return _repo.save(newModel);
        }
        //Update
        else {
            ProvinceCity existing=getById(model.getId());
            existing.setName(model.getName());
            return _repo.save(existing);
        }
    }
    @Override
    public void delete(Integer id) {
        _repo.deleteById(id);
    }
}
