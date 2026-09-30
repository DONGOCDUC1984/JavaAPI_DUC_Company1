package JavaAPI_DUC_Company1.specification;

import JavaAPI_DUC_Company1.model.Product;
import JavaAPI_DUC_Company1.model.dto.product.ProductInputSearchDTO;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
    //    Comparison
    //    C# EF Core	        Spring Boot
    //    IQueryable<Product>	    Specification<Product>
    //    .Where()	            Predicate
    //    .Include()	        @ManyToOne / fetch join if needed
    //    .CountAsync()	        page.getTotalElements()
    //    .Skip().Take()	    PageRequest
    //    .OrderBy()	        Sort.by("id")
    //    ToListAsync()	        Page<Product>
    public static Specification<Product> filter(
            ProductInputSearchDTO input) {

        return (root, query, builder) -> {
            List<Predicate> predicates =
                    new ArrayList<>();

            if (input.getSearchStr() != null && !input.getSearchStr().isBlank()) {
                String keyword = "%" + input.getSearchStr().trim().toLowerCase() + "%";
                predicates.add(
                        builder.or(builder.like(builder.lower(root.get("name")), keyword),
                        builder.like(builder.lower(root.get("description")), keyword)
                ));
            }

            if (input.getMinPrice() != null &&
                    input.getMinPrice().compareTo(BigDecimal.ZERO) > 0) {
                predicates.add(
                        builder.greaterThanOrEqualTo(root.get("price"), input.getMinPrice()));
            }

            if (input.getMaxPrice() != null &&
                    input.getMaxPrice().compareTo(BigDecimal.ZERO) > 0) {
                predicates.add(
                        builder.lessThanOrEqualTo(root.get("price"), input.getMaxPrice()));
            }

            if (input.getProductCategoryId() != null && input.getProductCategoryId() > 0) {
                predicates.add(
                        builder.equal(root.get("productCategory").get("id"), input.getProductCategoryId()));
            }

            if (input.getProvinceCityId() != null && input.getProvinceCityId() > 0) {
                predicates.add(
                        builder.equal(root.get("provinceCity").get("id"), input.getProvinceCityId()));
            }

            if (input.getDistrictId() != null && input.getDistrictId() > 0) {
                predicates.add(
                        builder.equal(root.get("district").get("id"), input.getDistrictId()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
