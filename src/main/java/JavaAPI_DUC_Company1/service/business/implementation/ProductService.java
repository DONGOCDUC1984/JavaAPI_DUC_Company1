package JavaAPI_DUC_Company1.service.business.implementation;

import JavaAPI_DUC_Company1.exception.ResourceNotFoundException;
import JavaAPI_DUC_Company1.mapper.ProductMapper;
import JavaAPI_DUC_Company1.model.District;
import JavaAPI_DUC_Company1.model.Product;
import JavaAPI_DUC_Company1.model.ProductCategory;
import JavaAPI_DUC_Company1.model.ProvinceCity;
import JavaAPI_DUC_Company1.model.dto.product.ProductAddUpdateDTO;
import JavaAPI_DUC_Company1.model.dto.product.ProductInputSearchDTO;
import JavaAPI_DUC_Company1.model.dto.product.ProductOutputSearchDTO;
import JavaAPI_DUC_Company1.model.paginatedlist.PaginatedListModel;
import JavaAPI_DUC_Company1.repository.DistrictRepository;
import JavaAPI_DUC_Company1.repository.ProductRepository;
import JavaAPI_DUC_Company1.repository.ProductCategoryRepository;
import JavaAPI_DUC_Company1.repository.ProvinceCityRepository;
import JavaAPI_DUC_Company1.service.common.interfaces.IImageStorageService;
import JavaAPI_DUC_Company1.service.business.interfaces.IProductService;
import JavaAPI_DUC_Company1.specification.ProductSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ProductService implements IProductService {
    private final ProductRepository _repo;
    private final ProductCategoryRepository _productCategoryRepo;
    private final ProvinceCityRepository _provinceCityRepo;
    private final DistrictRepository _districtRepo;
    private final IImageStorageService _imageService;
    private final ProductMapper _mapper;
    public  ProductService(ProductRepository repo,
                           ProductCategoryRepository productCategoryRepo, ProvinceCityRepository provinceCityRepo, DistrictRepository districtRepo, IImageStorageService imageService,
                           ProductMapper mapper){
        _repo=repo;
        _productCategoryRepo=productCategoryRepo;
        _provinceCityRepo = provinceCityRepo;
        _districtRepo = districtRepo;
        _imageService = imageService;
        _mapper = mapper;
    }

    @Override
    // With  @Transactional(readOnly = true),
    // Hibernate knows: "Nothing will be updated.".Therefore it skips most
    // of the dirty-checking work, reducing CPU and memory overhead for
    // read operations.
    @Transactional(readOnly = true)
    public PaginatedListModel<ProductOutputSearchDTO> getAll(
            ProductInputSearchDTO input) {
        //    Comparison
        //    C# EF Core	        Spring Boot
        //    IQueryable<Product>	    Specification<Product>
        //    .Where()	            Predicate
        //    .Include()	        @ManyToOne / fetch join if needed
        //    .CountAsync()	        page.getTotalElements()
        //    .Skip().Take()	    PageRequest
        //    .OrderBy()	        Sort.by("id")
        //    ToListAsync()	        Page<Product>
        Specification<Product> specification =
                ProductSpecification.filter(input);

        Pageable pageable =
                PageRequest.of(
                        input.getCurrentPage() - 1,
                        input.getPageSize(),
                        Sort.by("id"));

        Page<Product> page = _repo.findAll(specification, pageable);

        PaginatedListModel<ProductOutputSearchDTO> result = new PaginatedListModel<>();

        result.setItems(_mapper.toOutputSearchDTOList(page.getContent()));
        result.setCount(page.getTotalElements());
        result.setCurrentPage(input.getCurrentPage());
        result.setPageSize(input.getPageSize());
        result.setTotalPages(page.getTotalPages());

        return result;
    }

    @Override
    // With  @Transactional(readOnly = true),
    // Hibernate knows: "Nothing will be updated.".Therefore it skips most
    // of the dirty-checking work, reducing CPU and memory overhead for
    // read operations.
    @Transactional(readOnly = true)
    public Product getById(Integer id) {
        return _repo.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException
                        ("Product not found with id " + id) )
                ;
    }

    @Override
    public Product addUpdate(ProductAddUpdateDTO modelDTO
            , MultipartFile imageFile) {
        ProductCategory productCategory=_productCategoryRepo
                .findById(modelDTO.getProductCategoryId())
                .orElseThrow(() ->new RuntimeException("ProductCategory not found") );
        District district=_districtRepo
                .findById(modelDTO.getDistrictId())
                .orElseThrow(() ->new RuntimeException("District not found") );
        ProvinceCity provinceCity=_provinceCityRepo
                .findById(district.getProvinceCity().getId())
                .orElseThrow(() ->new RuntimeException("ProvinceCity not found") );
        Product product;
        //Add
        if (modelDTO.getId()==null || modelDTO.getId()==0){
            product=_mapper.toEntity(modelDTO);
        }
        //Update
        else {
            product=getById(modelDTO.getId());
            _mapper.updateEntity(modelDTO,product);
        }
        product.setProductCategory(productCategory);
        product.setProvinceCity(provinceCity);
        product.setDistrict(district);
        // Image
        if (imageFile != null && !imageFile.isEmpty()) {
            if (product.getImageUrl() != null) {
                _imageService.delete(product.getImageUrl());
            }

            String fileName = _imageService.save(imageFile);
            product.setImageUrl(fileName);
        }

        return _repo.save(product);
    }

    @Override
    public void delete(Integer id)
    {
        Product product=getById(id);
        if (product.getImageUrl() != null) {
            _imageService.delete(product.getImageUrl());
        }
        _repo.deleteById(id);
    }

}



