package JavaAPI_DUC_Company1.controller;

import JavaAPI_DUC_Company1.model.Product;
import JavaAPI_DUC_Company1.model.dto.product.ProductAddUpdateDTO;
import JavaAPI_DUC_Company1.model.dto.product.ProductInputSearchDTO;
import JavaAPI_DUC_Company1.model.dto.product.ProductOutputSearchDTO;
import JavaAPI_DUC_Company1.model.paginatedlist.PaginatedListModel;
import JavaAPI_DUC_Company1.service.common.interfaces.IJsonService;
import JavaAPI_DUC_Company1.service.business.interfaces.IProductService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/product")
@CrossOrigin
public class ProductController {
    private final IProductService _service;
    private final IJsonService _jsonService ;
    public ProductController(IProductService service,
                             IJsonService jsonService)
    {
        _service=service;
        _jsonService = jsonService;
    }

    @GetMapping
    public PaginatedListModel<ProductOutputSearchDTO> getAll( ProductInputSearchDTO input)
    {
        return _service.getAll(input);
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Integer id)
    {
        return _service.getById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public Product addUpdate( @RequestPart("document") String json,
                              @RequestPart(value = "imageFile", required = false)
                                  MultipartFile imageFile)
    {
        ProductAddUpdateDTO modelDTO =
                _jsonService.deserialize(json, ProductAddUpdateDTO.class);

        return _service.addUpdate(modelDTO,imageFile);

    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Integer id)
    {
        _service.delete(id);
    }
}


