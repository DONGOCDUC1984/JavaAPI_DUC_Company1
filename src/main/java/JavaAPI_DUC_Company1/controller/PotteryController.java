package JavaAPI_DUC_Company1.controller;

import JavaAPI_DUC_Company1.model.Pottery;
import JavaAPI_DUC_Company1.model.dto.pottery.PotteryAddUpdateDTO;
import JavaAPI_DUC_Company1.model.dto.pottery.PotteryInputSearchDTO;
import JavaAPI_DUC_Company1.model.dto.pottery.PotteryOutputSearchDTO;
import JavaAPI_DUC_Company1.model.paginatedlist.PaginatedListModel;
import JavaAPI_DUC_Company1.service.business.interfaces.IPotteryService;
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
@RequestMapping("/api/pottery")
@CrossOrigin
public class PotteryController {
    private final IPotteryService _service;
    public PotteryController(IPotteryService service)
    {
        _service=service;
    }

    @GetMapping
    public PaginatedListModel<PotteryOutputSearchDTO> search( PotteryInputSearchDTO input)
    {
        return _service.search(input);
    }

//    @GetMapping("/{id}")
//    public Pottery getById(@PathVariable Integer id)
//    {
//        return _service.getById(id);
//    }
//

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void addUpdate(@Valid @RequestBody PotteryAddUpdateDTO modelDTO)
    {
         _service.addUpdate(modelDTO);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void delete( @RequestBody List<Integer> ids) {
        _service.delete(ids);
    }

    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> export(PotteryInputSearchDTO input)
            throws Exception {

        ByteArrayInputStream excel = _service.exportToExcel(input);
        InputStreamResource resource = new InputStreamResource(excel);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=Potteries.xlsx")
                .contentType(MediaType.parseMediaType(
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(resource);
    }

}

