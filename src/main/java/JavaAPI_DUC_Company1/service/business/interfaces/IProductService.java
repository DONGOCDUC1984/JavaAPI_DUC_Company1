package JavaAPI_DUC_Company1.service.business.interfaces;

import JavaAPI_DUC_Company1.model.Product;
import JavaAPI_DUC_Company1.model.dto.product.ProductAddUpdateDTO;
import JavaAPI_DUC_Company1.model.dto.product.ProductInputSearchDTO;
import JavaAPI_DUC_Company1.model.dto.product.ProductOutputSearchDTO;
import JavaAPI_DUC_Company1.model.paginatedlist.PaginatedListModel;
import org.springframework.web.multipart.MultipartFile;

public interface IProductService {
    PaginatedListModel<ProductOutputSearchDTO> getAll(
            ProductInputSearchDTO input);
    Product getById(Integer id);
    Product addUpdate(ProductAddUpdateDTO modelDTO, MultipartFile imageFile);
    void delete(Integer id);
}


