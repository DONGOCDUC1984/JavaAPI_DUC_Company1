package JavaAPI_DUC_Company1.controller;

import JavaAPI_DUC_Company1.model.Department;
import JavaAPI_DUC_Company1.service.business.interfaces.IDepartmentService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/department")
//@CrossOrigin: Allow web pages from a different origin to access this controller.
// For example, this :
// http://localhost:3000
// is compatible with this controller.
@CrossOrigin
@PreAuthorize("hasRole('ADMIN')")
public class DepartmentController {
    private final IDepartmentService _service;
    public DepartmentController(IDepartmentService service)
    {
        _service=service;
    }

    @GetMapping
    public List<Department> getAll() {
        return _service.getAll();
    }

    @GetMapping("/{id}")
    public Department getById(@PathVariable Integer id)
    {
        return _service.getById(id);
    }

    @PostMapping
    public Department addUpdate(@Valid @RequestBody Department model)
    {
        return _service.addUpdate(model);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        _service.delete(id);
    }
}



