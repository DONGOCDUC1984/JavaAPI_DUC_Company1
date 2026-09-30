package JavaAPI_DUC_Company1.controller;

import JavaAPI_DUC_Company1.model.FurnitureCategory;
import JavaAPI_DUC_Company1.service.business.interfaces.IFurnitureCategoryService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/furnitureCategory")
//@CrossOrigin: Allow web pages from a different origin to access this controller.
// For example, this :
// http://localhost:3000
// is compatible with this controller.
@CrossOrigin
public class FurnitureCategoryController {
    private final IFurnitureCategoryService _service;
    public FurnitureCategoryController(IFurnitureCategoryService service)
    {
        _service=service;
    }

    @GetMapping
    public List<FurnitureCategory> getAll() {
        return _service.getAll();
    }

    @GetMapping("/{id}")
    public FurnitureCategory getById(@PathVariable Integer id)
    {
        return _service.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public FurnitureCategory addUpdate(@Valid @RequestBody FurnitureCategory model)
    {
        return _service.addUpdate(model);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Integer id) {
        _service.delete(id);
    }
}



