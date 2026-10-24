package JavaAPI_DUC_Company1.specification;

import JavaAPI_DUC_Company1.model.Gender;
import JavaAPI_DUC_Company1.model.Staff;
import JavaAPI_DUC_Company1.model.dto.staff.StaffInputSearchDTO;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class StaffSpecification {
    //    Comparison
//    C# EF Core	        Spring Boot
//    IQueryable<Staff>	    Specification<Staff>
//    .Where()	            Predicate
//    .Include()	        @ManyToOne / fetch join if needed
//    .CountAsync()	        page.getTotalElements()
//    .Skip().Take()	    PageRequest
//    .OrderBy()	        Sort.by("id")
//    ToListAsync()	        Page<Staff>
    public static Specification<Staff> filter(
            StaffInputSearchDTO input) {

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
//                The following Expression<String> fullName ...means
//                If:
//                lastName  = Do
//                middleName = Duc
//                firstName = Ngoc
//
//                it produces:
//                do duc ngoc
//
//                If:
//                lastName  = Do
//                middleName = null or ""
//                firstName = Ngoc
//
//                it produces:
//                do ngoc
//                So I don't get:
//                do  ngoc

                Expression<String> fullName = builder.lower(
                        builder.<String>selectCase()
                                .when(
                                        builder.or(
                                                builder.isNull(root.get("middleName")),
                                                builder.equal(root.get("middleName"), "")
                                        ),
                                        builder.concat(
                                                builder.concat(root.get("lastName"), " "),
                                                root.get("firstName")
                                        )
                                )
                                .otherwise(
                                        builder.concat(
                                                builder.concat(
                                                        builder.concat(root.get("lastName"), " "),
                                                        root.get("middleName")
                                                ),
                                                builder.concat(" ", root.get("firstName"))
                                        )
                                )
                );

                predicates.add(builder.like(fullName,keyword ));
            }

            if (input.getGender() != null && !input.getGender().isBlank()) {
                try {
                    Gender gender = Gender.valueOf(input.getGender().trim());
                    predicates.add(builder.equal(root.get("gender"), gender));

                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid gender: "
                            + input.getGender());
                }
            }

            if (input.getProvinceCityId() != null && input.getProvinceCityId() > 0) {
                predicates.add(builder.equal(root.get("provinceCity").get("id"),
                                input.getProvinceCityId()));
            }

            if (input.getDepartmentId() != null && input.getDepartmentId() > 0) {
                predicates.add(builder.equal(root.get("department").get("id"),
                        input.getDepartmentId()));
            }

            if (input.getPositionId() != null && input.getPositionId() > 0) {
                predicates.add(builder.equal(root.get("position").get("id"),
                        input.getPositionId()));
            }

            if (input.getStartHireDate() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("hireDate"),
                                input.getStartHireDate()));
            }

            if (input.getEndHireDate() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("hireDate"),
                                input.getEndHireDate()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
