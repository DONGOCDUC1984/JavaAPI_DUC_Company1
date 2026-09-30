package JavaAPI_DUC_Company1.mapper;

import JavaAPI_DUC_Company1.model.Product;
import JavaAPI_DUC_Company1.model.dto.product.ProductAddUpdateDTO;
import JavaAPI_DUC_Company1.model.dto.product.ProductOutputSearchDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    //ignore = true because a new item does not need id to be created .
    @Mapping(target = "id", ignore = true)
    //ignore = true because MapStruct cannot convert
    // from Integer (productCategoryId) to ProductCategory
    @Mapping(target = "productCategory", ignore = true)
    @Mapping(target = "provinceCity", ignore = true)
    @Mapping(target = "district", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    Product toEntity(ProductAddUpdateDTO modelDTO);

    @Mapping(target = "productCategory", ignore = true)
    @Mapping(target = "provinceCity", ignore = true)
    @Mapping(target = "district", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    void updateEntity(ProductAddUpdateDTO modelDTO, @MappingTarget Product product);

    @Mapping(source = "productCategory.id", target = "productCategoryId")
    @Mapping(source = "productCategory.englishName", target = "productCategoryEnglishName")
    @Mapping(source = "provinceCity.id", target = "provinceCityId")
    @Mapping(source = "provinceCity.name", target = "provinceCityName")
    @Mapping(source = "district.id", target = "districtId")
    @Mapping(source = "district.name", target = "districtName")
    ProductOutputSearchDTO toOutputSearchDTO(Product product);

    List<ProductOutputSearchDTO> toOutputSearchDTOList(List<Product> products);
}