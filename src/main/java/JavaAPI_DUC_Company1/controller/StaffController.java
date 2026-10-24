package JavaAPI_DUC_Company1.controller;

import JavaAPI_DUC_Company1.model.Staff;
import JavaAPI_DUC_Company1.model.dto.staff.StaffAddUpdateDTO;
import JavaAPI_DUC_Company1.model.dto.staff.StaffInputSearchDTO;
import JavaAPI_DUC_Company1.model.dto.staff.StaffOutputSearchDTO;
import JavaAPI_DUC_Company1.model.paginatedlist.PaginatedListModel;
import JavaAPI_DUC_Company1.service.business.interfaces.IStaffService;
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
@RequestMapping("/api/staff")
@CrossOrigin
@PreAuthorize("hasRole('ADMIN')")
public class StaffController {
    private final IStaffService _service;
    public StaffController(IStaffService service)
    {
        _service=service;
    }

    @GetMapping
    public PaginatedListModel<StaffOutputSearchDTO> search( StaffInputSearchDTO input)
    {
        return _service.search(input);
    }

    @GetMapping("/{id}")
    public Staff getById(@PathVariable Integer id)
    {
        return _service.getById(id);
    }

    @PostMapping
    public void addUpdate(@Valid @RequestBody StaffAddUpdateDTO modelDTO)
    {
        _service.addUpdate(modelDTO);
    }

    @DeleteMapping
    public void delete( @RequestBody List<Integer> ids) {
        _service.delete(ids);
    }

    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> export(StaffInputSearchDTO input)
            throws Exception {

        ByteArrayInputStream excel = _service.exportToExcel(input);
        InputStreamResource resource = new InputStreamResource(excel);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=Staffs.xlsx")
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(resource);
    }

}


