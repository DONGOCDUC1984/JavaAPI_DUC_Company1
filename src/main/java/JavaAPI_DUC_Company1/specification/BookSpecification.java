package JavaAPI_DUC_Company1.specification;

import JavaAPI_DUC_Company1.model.Book;
import JavaAPI_DUC_Company1.model.dto.book.BookInputSearchDTO;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class BookSpecification {
//    Comparison
//    C# EF Core	        Spring Boot
//    IQueryable<Book>	    Specification<Book>
//    .Where()	            Predicate
//    .Include()	        @ManyToOne / fetch join if needed
//    .CountAsync()	        page.getTotalElements()
//    .Skip().Take()	    PageRequest
//    .OrderBy()	        Sort.by("id")
//    ToListAsync()	        Page<Book>
    public static Specification<Book> filter(
            BookInputSearchDTO input) {

        return (root, query, builder) -> {
            List<Predicate> predicates =
                    new ArrayList<>();

            if (input.getSearchStr() != null && !input.getSearchStr().isBlank()) {
                String keyword = "%" + input.getSearchStr().trim()
                        .toLowerCase()
                        // .replaceAll("\\s+", " ") means replace
                        // _one or more consecutive whitespace characters such as "     "
                        //by
                        // _one normal space (" ").
                        .replaceAll("\\s+", " ") + "%";
                predicates.add(builder.or(
                        builder.like(builder.lower(root.get("name")), keyword),
                        builder.like(builder.lower(root.get("colour")), keyword)
                        ));
            }

            if (input.getColour() != null && !input.getColour().isBlank()) {
                predicates.add(
                        builder.equal(root.get("colour"), input.getColour()));
            }

            if (input.getIsMadeInVietnam() != null) {
                predicates.add(
                        builder.equal(root.get("isMadeInVietnam"), input.getIsMadeInVietnam()));
            }

            if (input.getBookCategoryId() != null && input.getBookCategoryId() > 0) {
                predicates.add(
                        builder.equal(root.get("bookCategory").get("id"), input.getBookCategoryId()));
            }

            if (input.getStartTime() != null) {
                predicates.add(
                        builder.greaterThanOrEqualTo(root.get("publishedDay"), input.getStartTime()));
            }

            if (input.getEndTime() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("publishedDay"), input.getEndTime()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}