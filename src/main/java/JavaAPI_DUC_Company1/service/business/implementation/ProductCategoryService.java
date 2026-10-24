package JavaAPI_DUC_Company1.service.business.implementation;

import JavaAPI_DUC_Company1.model.ProductCategory;
import JavaAPI_DUC_Company1.exception.ResourceNotFoundException;
import JavaAPI_DUC_Company1.repository.ProductCategoryRepository;
import JavaAPI_DUC_Company1.service.business.interfaces.IProductCategoryService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductCategoryService implements IProductCategoryService {
    private final ProductCategoryRepository _repo;
    public  ProductCategoryService(ProductCategoryRepository repo){
        _repo=repo;
    }

    @Override
    @Cacheable(value = "productCategories", key = "'all'")
    public List<ProductCategory> getAll() {
        //System.out.println("Fetching productCategories from the database...");
        return _repo.findAll();
    }

    @Override
    public ProductCategory getById(Integer id) {
        return _repo.findById(id)
//                      .orElseThrow(() -> new RuntimeException("ProductCategory not found with id " + id));
// The 2 following lines can be replaced by the above line
                .orElseThrow(()->new ResourceNotFoundException("ProductCategory not found with id " + id) )
                ;
    }
//    @Override
//    @CacheEvict(value = "productCategories", allEntries = true)
//    public ProductCategory create(ProductCategory model) {
//        return _repo.save(model);
//    }
//    @Override
//    @CacheEvict(value = "productCategories", allEntries = true)
//    public ProductCategory update(Integer id, ProductCategory model) {
//        ProductCategory existing=getById(id);
//        existing.setEnglishName(model.getEnglishName());
//        existing.setVietnameseName(model.getVietnameseName());
//        return _repo.save(existing);
//    }
    @Override
    //value = "productCategories" means: "productCategories" is cache name.
    //allEntries = true means :Remove everything stored in this cache.
    @CacheEvict(value = "productCategories", allEntries = true)
    public ProductCategory addUpdate(ProductCategory model) {
        //Add
        if (model.getId()==null || model.getId()==0){
            ProductCategory newModel =new ProductCategory();
            newModel.setEnglishName(model.getEnglishName());
            newModel.setVietnameseName(model.getVietnameseName());
            return _repo.save(newModel);
        }
        //Update
        else {
            ProductCategory existing=getById(model.getId());
            existing.setEnglishName(model.getEnglishName());
            existing.setVietnameseName(model.getVietnameseName());
            return _repo.save(existing);
        }
    }
    @Override
    //value = "productCategories" means: "productCategories" is cache name.
    //allEntries = true means :Remove everything stored in this cache.
    @CacheEvict(value = "productCategories", allEntries = true)
    public void delete(Integer id) {
        _repo.deleteById(id);
    }
}
