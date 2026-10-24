package JavaAPI_DUC_Company1.service.business.interfaces;

import JavaAPI_DUC_Company1.model.ProvinceCity;
import java.util.List;

public interface IProvinceCityService {
    List<ProvinceCity> getAll();
    ProvinceCity getById(Integer id);
    ProvinceCity addUpdate(ProvinceCity model);
    void delete(Integer id);
}
