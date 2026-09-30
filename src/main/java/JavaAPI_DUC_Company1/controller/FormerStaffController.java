package JavaAPI_DUC_Company1.controller;

import JavaAPI_DUC_Company1.model.FormerStaff;
import JavaAPI_DUC_Company1.model.dto.formerStaff.FormerStaffInputSearchDTO;
import JavaAPI_DUC_Company1.model.dto.formerStaff.FormerStaffOutputSearchDTO;
import JavaAPI_DUC_Company1.model.paginatedlist.PaginatedListModel;
import JavaAPI_DUC_Company1.service.business.interfaces.IFormerStaffService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/formerStaff")
@CrossOrigin
public class FormerStaffController {
    private final IFormerStaffService _service;
    public FormerStaffController(IFormerStaffService service)
    {
        _service=service;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public PaginatedListModel<FormerStaffOutputSearchDTO> search( FormerStaffInputSearchDTO input)
    {
        return _service.search(input);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public FormerStaff getById(@PathVariable Integer id)
    {
        return _service.getById(id);
    }


}



