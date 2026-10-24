package JavaAPI_DUC_Company1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "staffs",
        // I can create this table first.
        // After some days or even some weeks, I can add indexes like this :
        indexes = {
                @Index(
                        name = "idx_staff_first_name",
                        columnList = "first_name"),
                @Index(
                        name = "idx_staff_middle_name",
                        columnList = "middle_name"),
                @Index(
                        name = "idx_staff_last_name",
                        columnList = "last_name"),
                @Index(
                        name = "idx_staff_address",
                        columnList = "address"),
                @Index(
                        name = "idx_staff_tel",
                        columnList = "tel"),
                @Index(
                        name = "idx_staff_email",
                        columnList = "email"),
                @Index(
                        name = "idx_staff_salary",
                        columnList = "salary"),
                @Index(
                        name = "idx_staff_citizenidnumber",
                        columnList = "citizenidnumber"),
                // gender has only 2 values
                // so it is unnecessary to create its index
                @Index(
                        name = "idx_staff_province_city",
                        columnList = "province_city_id"),
                @Index(
                        name = "idx_staff_department",
                        columnList = "department_id"),
                @Index(
                        name = "idx_staff_position",
                        columnList = "position_id"),
                @Index(
                        name = "idx_staff_hire_date",
                        columnList = "hire_date"),
                @Index(
                        name = "idx_staff_end_date",
                        columnList = "end_date"),
                @Index(
                        name = "idx_staff_birth_date",
                        columnList = "birth_date"),
                // Composite Index since the search often combines
                // salary, citizenidnumber," +
                //"province_city_id,department_id, position_id," +
                //"hire_date,end_date,birth_date
                @Index(
                        name = "idx_staff_search",
                        columnList = "salary, citizenidnumber," +
                                "province_city_id,department_id, position_id," +
                                "hire_date,end_date,birth_date "),

        })
@Getter
@Setter
public class Staff  {
    @Id
    //Use the database's AUTO_INCREMENT
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Here is Integer because Integer can be null.int cannot be null.
    private Integer id;

    @Column(nullable = false, length = 20)
    @NotBlank(message = "FirstName is required")
    private String firstName;

    private String middleName;

    @Column(nullable = false, length = 20)
    @NotBlank(message = "LastName is required")
    private String lastName;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "Address is required")
    private String address;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Telophone number is required")
    private String tel;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Email is required")
    private String email;

    @Column(nullable = false)
    @NotNull(message = "Salary is required")
    private Integer salary;

    @Column(nullable = false)
    @NotBlank(message = "CitizenIDNumber is required")
    private String citizenIDNumber;

    @Column(nullable = false)
    //Do not write @NotBlank since @NotBlank is only for String/CharSequence values,
    // whereas Gender is an enum
    @NotNull(message = "Gender is required")
    //@Enumerated(EnumType.STRING): the database contains: Male,Female
    @Enumerated(EnumType.STRING)
    private Gender gender ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( nullable = false)
    private ProvinceCity provinceCity;

    // If I do not add Department here, there will be no DepartmentId ,
    // and it will take at least 20 seconds to search by Department with just 1200 Staffs.
    // If I add Department here, there will be DepartmentId with index,
    // and it will take only 1 second to search by Department with over 100,000 Staffs.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( nullable = false)
    private Position position;

    @Column(nullable = false)
    @NotNull(message = "HireDate is required")
    // I should utilize LocalDate not Date here.Since
    // _Date contains Year, Month, Day, Hour, Minute, Second, Millisecond
    // _LocalDate contains only Year, Month, Day
    private LocalDate hireDate;

    private LocalDate endDate;

    @Column(nullable = false)
    @NotNull(message = "BirthDate is required")
    private LocalDate birthDate;

    public Staff() {
    }

    public Staff( String firstName, String middleName,
                  String lastName, String address, String tel, String email,
                  Integer salary, String citizenIDNumber, Gender gender,
                  ProvinceCity provinceCity, Department department,
                  Position position, LocalDate hireDate, LocalDate endDate,
                  LocalDate birthDate) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.address = address;
        this.tel = tel;
        this.email = email;
        this.salary = salary;
        this.citizenIDNumber = citizenIDNumber;
        this.gender = gender;
        this.provinceCity = provinceCity;
        this.department = department;
        this.position = position;
        this.hireDate = hireDate;
        this.endDate = endDate;
        this.birthDate = birthDate;
    }
}



