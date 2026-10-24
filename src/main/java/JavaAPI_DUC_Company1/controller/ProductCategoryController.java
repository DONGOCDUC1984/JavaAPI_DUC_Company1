package JavaAPI_DUC_Company1.controller;

import JavaAPI_DUC_Company1.model.ProductCategory;
import JavaAPI_DUC_Company1.service.business.interfaces.IProductCategoryService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productCategory")
//@CrossOrigin: Allow web pages from a different origin to access this controller.
// For example, this :
// http://localhost:3000
// is compatible with this controller.
@CrossOrigin
public class ProductCategoryController {
    private final IProductCategoryService _service;
    public ProductCategoryController(IProductCategoryService service)
    {
        _service=service;
    }

    @GetMapping
    public List<ProductCategory> getAll() {
        return _service.getAll();
    }

    @GetMapping("/{id}")
    public ProductCategory getById(@PathVariable Integer id)
    {
        return _service.getById(id);
    }

//    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public ProductCategory create(@Valid @RequestBody ProductCategory model)
//    {
//        return _service.create(model);
//    }
//
//    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ProductCategory update(@PathVariable Integer id,
//                                  @Valid @RequestBody ProductCategory model)
//    {
//        return _service.update(id,model);
//    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ProductCategory addUpdate(@Valid @RequestBody ProductCategory model)
    {
        return _service.addUpdate(model);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Integer id) {
        _service.delete(id);
    }
}
