package JavaAPI_DUC_Company1.model.dto.staff;

import JavaAPI_DUC_Company1.model.Gender;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class StaffOutputSearchDTO {
    private Integer id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String address;
    private String tel;
    private String email;
    private Integer salary;
    private String citizenIDNumber;
    private Gender gender;
    private Integer provinceCityId;
    private String provinceCityName;
    private Integer departmentId;
    private String departmentEnglishName;
    private String departmentVietnameseName;
    private Integer positionId;
    private String positionEnglishName;
    private String positionVietnameseName;
    private LocalDate hireDate;
    private LocalDate endDate;
    private LocalDate birthDate;

    public StaffOutputSearchDTO() {
    }

}

