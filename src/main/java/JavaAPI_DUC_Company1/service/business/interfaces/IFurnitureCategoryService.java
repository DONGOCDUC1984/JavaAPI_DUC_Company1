package JavaAPI_DUC_Company1.service.business.interfaces;

import JavaAPI_DUC_Company1.model.FurnitureCategory;
import java.util.List;

public interface IFurnitureCategoryService {
    List<FurnitureCategory> getAll();
    FurnitureCategory getById(Integer id);
    FurnitureCategory addUpdate(FurnitureCategory model);
    void delete(Integer id);
}


