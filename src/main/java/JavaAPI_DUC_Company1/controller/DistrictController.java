package JavaAPI_DUC_Company1.controller;

import JavaAPI_DUC_Company1.model.District;
import JavaAPI_DUC_Company1.model.dto.district.DistrictAddUpdateDTO;
import JavaAPI_DUC_Company1.service.business.interfaces.IDistrictService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/district")
@CrossOrigin
public class DistrictController {
    private final IDistrictService _service;
    public DistrictController(IDistrictService service)
    {
        _service=service;
    }

    @GetMapping
    public List<District> getAll() {
        return _service.getAll();
    }

    @GetMapping("/{id}")
    public District getById(@PathVariable Integer id)
    {
        return _service.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public District addUpdate(@Valid @RequestBody DistrictAddUpdateDTO modelDTO)
    {
        return _service.addUpdate(modelDTO);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Integer id) {
        _service.delete(id);
    }
}

