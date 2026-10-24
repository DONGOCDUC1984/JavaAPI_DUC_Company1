package JavaAPI_DUC_Company1.service.business.implementation;

import JavaAPI_DUC_Company1.model.District;
import JavaAPI_DUC_Company1.model.ProvinceCity;
import JavaAPI_DUC_Company1.model.dto.district.DistrictAddUpdateDTO;
import JavaAPI_DUC_Company1.repository.DistrictRepository;
import JavaAPI_DUC_Company1.repository.ProvinceCityRepository;
import JavaAPI_DUC_Company1.service.business.interfaces.IDistrictService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@Transactional
public class DistrictService implements IDistrictService {
    private final DistrictRepository _repo;
    private final ProvinceCityRepository _provinceCityRepo;
    public  DistrictService(DistrictRepository repo,
                            ProvinceCityRepository provinceCityRepo){
        _repo=repo;
        _provinceCityRepo=provinceCityRepo;
    }

    @Override
    public List<District> getAll() {
        return _repo.findAll();
    }

    @Override
    public District getById(Integer id) {
        return _repo.findById(id)
                .orElseThrow(() -> new RuntimeException("District not found with id " + id));
    }

    @Override
    public District addUpdate(DistrictAddUpdateDTO modelDTO) {
        ProvinceCity provinceCity=_provinceCityRepo
                .findById(modelDTO.getProvinceCityId())
                .orElseThrow(() ->new RuntimeException("ProvinceCity not found"));
        //Add
        if (modelDTO.getId()==null || modelDTO.getId()==0){
            District newModel =new District();
            newModel.setName(modelDTO.getName());
            newModel.setProvinceCity(provinceCity);
            return _repo.save(newModel);
        }
        //Update
        else {
            District existing=getById(modelDTO.getId());
            existing.setName(modelDTO.getName());
            existing.setProvinceCity(provinceCity);
            return _repo.save(existing);
        }
    }
    @Override
    public void delete(Integer id) {
        _repo.deleteById(id);
    }
}

