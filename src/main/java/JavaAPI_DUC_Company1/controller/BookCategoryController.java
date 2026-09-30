package JavaAPI_DUC_Company1.controller;

import JavaAPI_DUC_Company1.model.BookCategory;
import JavaAPI_DUC_Company1.service.business.interfaces.IBookCategoryService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookCategory")
//@CrossOrigin: Allow web pages from a different origin to access this controller.
// For example, this :
// http://localhost:3000
// is compatible with this controller.
@CrossOrigin
public class BookCategoryController {
    private final IBookCategoryService _service;
    public BookCategoryController(IBookCategoryService service)
    {
        _service=service;
    }

    @GetMapping
    public List<BookCategory> getAll() {
        return _service.getAll();
    }

    @GetMapping("/{id}")
    public BookCategory getById(@PathVariable Integer id)
    {
        return _service.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public BookCategory addUpdate(@Valid @RequestBody BookCategory model)
    {
        return _service.addUpdate(model);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Integer id) {
        _service.delete(id);
    }
}

