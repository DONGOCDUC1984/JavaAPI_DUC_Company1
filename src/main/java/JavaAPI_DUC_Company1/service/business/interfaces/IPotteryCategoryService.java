package JavaAPI_DUC_Company1.service.business.interfaces;

import JavaAPI_DUC_Company1.model.PotteryCategory;
import java.util.List;

public interface IPotteryCategoryService {
    List<PotteryCategory> getAll();
    PotteryCategory getById(Integer id);
    PotteryCategory addUpdate(PotteryCategory model);
    void delete(Integer id);
}

