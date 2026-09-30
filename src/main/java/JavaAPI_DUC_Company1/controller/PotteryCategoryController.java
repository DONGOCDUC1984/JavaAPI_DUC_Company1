package JavaAPI_DUC_Company1.controller;

import JavaAPI_DUC_Company1.model.PotteryCategory;
import JavaAPI_DUC_Company1.service.business.interfaces.IPotteryCategoryService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/potteryCategory")
//@CrossOrigin: Allow web pages from a different origin to access this controller.
// For example, this :
// http://localhost:3000
// is compatible with this controller.
@CrossOrigin
public class PotteryCategoryController {
    private final IPotteryCategoryService _service;
    public PotteryCategoryController(IPotteryCategoryService service)
    {
        _service=service;
    }

    @GetMapping
    public List<PotteryCategory> getAll() {
        return _service.getAll();
    }

    @GetMapping("/{id}")
    public PotteryCategory getById(@PathVariable Integer id)
    {
        return _service.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public PotteryCategory addUpdate(@Valid @RequestBody PotteryCategory model)
    {
        return _service.addUpdate(model);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Integer id) {
        _service.delete(id);
    }
}


