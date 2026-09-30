package JavaAPI_DUC_Company1.service.business.interfaces;

import JavaAPI_DUC_Company1.model.ProductCategory;

import java.util.List;

public interface IProductCategoryService {
    List<ProductCategory> getAll();
    ProductCategory getById(Integer id);
//    ProductCategory create(ProductCategory model);
//    ProductCategory update(Integer id, ProductCategory model);
    ProductCategory addUpdate(ProductCategory model);
    void delete(Integer id);
}
