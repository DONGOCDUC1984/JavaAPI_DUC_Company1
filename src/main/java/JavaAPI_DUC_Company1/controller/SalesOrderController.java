package JavaAPI_DUC_Company1.controller;

import JavaAPI_DUC_Company1.model.SalesOrder;
import JavaAPI_DUC_Company1.model.dto.salesOrder.SalesOrderAddUpdateDTO;
import JavaAPI_DUC_Company1.model.dto.salesOrder.SalesOrderReadDTO;
import JavaAPI_DUC_Company1.service.business.interfaces.ISalesOrderService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/salesOrder")
@CrossOrigin
public class SalesOrderController {
    private final ISalesOrderService _service;
    public SalesOrderController(ISalesOrderService service)
    {
        _service=service;
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public List<SalesOrderReadDTO> getAll() {
        return _service.getAll();
    }

    @GetMapping
    public List<SalesOrderReadDTO> getByUserId() {
        return _service.getByUserId();
    }

    @GetMapping("/{id}")
    public SalesOrder getById(@PathVariable Integer id)
    {
        return _service.getById(id);
    }

    @PostMapping
    public void add(@Valid @RequestBody SalesOrderAddUpdateDTO modelDTO)
    {
         _service.add(modelDTO);
    }

    //I should not have this delete method since :
// _A sales order is usually business history.
// If I delete it,I lose the historical record.
// _I can replace it by markCancelled method.
// If I want to delete a SalesOrder, I will have to delete its SalesOrderItem first.
// Just check in MySQL Workbench .
//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public void delete(@PathVariable Integer id) {
//        _service.delete(id);
//    }

    @GetMapping("/markDelivered/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void markDelivered(@PathVariable Integer id)
    {
         _service.markDelivered(id);
    }

    @GetMapping("/markCancelled/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void markCancelled(@PathVariable Integer id)
    {
        _service.markCancelled(id);
    }

}


