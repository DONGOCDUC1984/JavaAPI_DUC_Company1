package JavaAPI_DUC_Company1.controller;

import JavaAPI_DUC_Company1.model.Book;
import JavaAPI_DUC_Company1.model.dto.book.BookAddUpdateDTO;
import JavaAPI_DUC_Company1.model.dto.book.BookInputSearchDTO;
import JavaAPI_DUC_Company1.model.dto.book.BookOutputSearchDTO;
import JavaAPI_DUC_Company1.model.paginatedlist.PaginatedListModel;
import JavaAPI_DUC_Company1.service.business.interfaces.IBookService;
import jakarta.validation.Valid;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/api/book")
@CrossOrigin
public class BookController {
    private final IBookService _service;
    public BookController(IBookService service)
    {
        _service=service;
    }

    @GetMapping
    public PaginatedListModel<BookOutputSearchDTO> search( BookInputSearchDTO input)
    {
        return _service.search(input);
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable Integer id)
    {
        return _service.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void addUpdate(@Valid @RequestBody BookAddUpdateDTO modelDTO)
    {
         _service.addUpdate(modelDTO);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void delete( @RequestBody List<Integer> ids) {
        _service.delete(ids);
    }

    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> export(BookInputSearchDTO input)
            throws Exception {

        ByteArrayInputStream excel = _service.exportToExcel(input);
        InputStreamResource resource = new InputStreamResource(excel);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=Books.xlsx")
                .contentType(MediaType.parseMediaType(
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(resource);
    }

}

