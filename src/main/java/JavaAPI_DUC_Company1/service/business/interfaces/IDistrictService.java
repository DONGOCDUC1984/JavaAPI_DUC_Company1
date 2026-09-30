package JavaAPI_DUC_Company1.service.business.interfaces;

import JavaAPI_DUC_Company1.model.District;
import JavaAPI_DUC_Company1.model.dto.district.DistrictAddUpdateDTO;

import java.util.List;

public interface IDistrictService {
    List<District> getAll();
    District getById(Integer id);
    District addUpdate(DistrictAddUpdateDTO modelDTO);
    void delete(Integer id);
}
