package JavaAPI_DUC_Company1.controller;

import JavaAPI_DUC_Company1.model.Furniture;
import JavaAPI_DUC_Company1.model.dto.furniture.FurnitureAddUpdateDTO;
import JavaAPI_DUC_Company1.model.dto.furniture.FurnitureInputSearchDTO;
import JavaAPI_DUC_Company1.model.dto.furniture.FurnitureOutputSearchDTO;
import JavaAPI_DUC_Company1.model.paginatedlist.PaginatedListModel;
import JavaAPI_DUC_Company1.service.business.interfaces.IFurnitureService;
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
@RequestMapping("/api/furniture")
@CrossOrigin
public class FurnitureController {
    private final IFurnitureService _service;
    public FurnitureController(IFurnitureService service)
    {
        _service=service;
    }

    @GetMapping
    public PaginatedListModel<FurnitureOutputSearchDTO> search( FurnitureInputSearchDTO input)
    {
        return _service.search(input);
    }

//    @GetMapping("/{id}")
//    public Furniture getById(@PathVariable Integer id)
//    {
//        return _service.getById(id);
//    }
//

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void addUpdate(@Valid @RequestBody FurnitureAddUpdateDTO modelDTO)
    {
        _service.addUpdate(modelDTO);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void delete( @RequestBody List<Integer> ids) {
        _service.delete(ids);
    }
//
//    @GetMapping("/export")
//    public ResponseEntity<InputStreamResource> export(FurnitureInputSearchDTO input)
//            throws Exception {
//
//        ByteArrayInputStream excel = _service.exportToExcel(input);
//        InputStreamResource resource = new InputStreamResource(excel);
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION,
//                        "attachment; filename=Potteries.xlsx")
//                .contentType(MediaType.parseMediaType(
//                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
//                .body(resource);
//    }

}


