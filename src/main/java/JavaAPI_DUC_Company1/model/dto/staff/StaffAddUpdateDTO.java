package JavaAPI_DUC_Company1.model.dto.staff;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class StaffAddUpdateDTO {
    private Integer id;

    @NotBlank(message = "FirstName is required")
    private String firstName;

    private String middleName;

    @NotBlank(message = "LastName is required")
    private String lastName;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Tel is required")
    private String tel;

    @NotBlank(message = "Email is required")
    private String email;

    @NotNull(message = "Salary is required")
    private Integer salary;

    @NotBlank(message = "CitizenIDNumber is required")
    private String citizenIDNumber;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotNull(message = "provinceCity is required")
    private Integer provinceCityId;

    @NotNull(message = "Department is required")
    private Integer departmentId;

    @NotNull(message = "Position is required")
    private Integer positionId;

    @NotNull(message = "HireDate is required")
    private LocalDate hireDate;

    private LocalDate endDate;

    @NotNull(message = "BirthDate is required")
    private LocalDate birthDate;
}

