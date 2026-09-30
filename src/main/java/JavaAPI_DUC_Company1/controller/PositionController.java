package JavaAPI_DUC_Company1.controller;

import JavaAPI_DUC_Company1.model.Position;
import JavaAPI_DUC_Company1.model.dto.position.PositionAddUpdateDTO;
import JavaAPI_DUC_Company1.model.dto.position.PositionOutputSearchDTO;
import JavaAPI_DUC_Company1.service.business.interfaces.IPositionService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/position")
//@CrossOrigin: Allow web pages from a different origin to access this controller.
// For example, this :
// http://localhost:3000
// is compatible with this controller.
@CrossOrigin
@PreAuthorize("hasRole('ADMIN')")
public class PositionController {
    private final IPositionService _service;
    public PositionController(IPositionService service)
    {
        _service=service;
    }

    @GetMapping
    public List<PositionOutputSearchDTO> getAll() {
        return _service.getAll();
    }

    @GetMapping("/{id}")
    public Position getById(@PathVariable Integer id)
    {
        return _service.getById(id);
    }

    @PostMapping
    public Position addUpdate(@Valid @RequestBody PositionAddUpdateDTO modelDTO)
    {
        return _service.addUpdate(modelDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        _service.delete(id);
    }
}



