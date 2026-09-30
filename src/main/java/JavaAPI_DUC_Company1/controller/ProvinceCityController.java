package JavaAPI_DUC_Company1.controller;

import JavaAPI_DUC_Company1.model.ProvinceCity;
import JavaAPI_DUC_Company1.service.business.interfaces.IProvinceCityService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/provinceCity")
//@CrossOrigin: Allow web pages from a different origin to access this controller.
// For example, this :
// http://localhost:3000
// is compatible with this controller.
@CrossOrigin
public class ProvinceCityController {
    private final IProvinceCityService _service;
    public ProvinceCityController(IProvinceCityService service)
    {
        _service=service;
    }

    @GetMapping
    public List<ProvinceCity> getAll() {
        return _service.getAll();
    }

    @GetMapping("/{id}")
    public ProvinceCity getById(@PathVariable Integer id)
    {
        return _service.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ProvinceCity addUpdate(@Valid @RequestBody ProvinceCity model)
    {
        return _service.addUpdate(model);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Integer id) {
        _service.delete(id);
    }
}
