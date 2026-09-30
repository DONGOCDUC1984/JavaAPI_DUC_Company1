package JavaAPI_DUC_Company1.seeder;

import JavaAPI_DUC_Company1.model.Department;
import JavaAPI_DUC_Company1.model.Position;
import JavaAPI_DUC_Company1.model.PositionCategory;
import JavaAPI_DUC_Company1.model.dto.position.PositionAddUpdateDTO;
import JavaAPI_DUC_Company1.repository.DepartmentRepository;
import JavaAPI_DUC_Company1.repository.PositionCategoryRepository;
import JavaAPI_DUC_Company1.repository.PositionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeePositionSeeder implements CommandLineRunner {

    private final PositionRepository positionRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionCategoryRepository positionCategoryRepository;

    public EmployeePositionSeeder(
            PositionRepository positionRepository,
            DepartmentRepository departmentRepository,
            PositionCategoryRepository positionCategoryRepository) {

        this.positionRepository = positionRepository;
        this.departmentRepository = departmentRepository;
        this.positionCategoryRepository = positionCategoryRepository;
    }

    public List<PositionAddUpdateDTO> getPositionAddUpdateDTOs() {

        return List.of(

                // DepartmentId = 1 (Accounting)
                new PositionAddUpdateDTO(
                        "Accountant",
                        "Kế toán viên",
                        1,
                        1),

                new PositionAddUpdateDTO(
                        "Junior Accountant",
                        "Kế toán tập sự",
                        1,
                        1
                ),

                new PositionAddUpdateDTO(
                        "Accounting Clerk",
                        "Nhân viên kế toán",
                        1,
                        1
                ),

                // DepartmentId = 2 (Customer Service)
                new PositionAddUpdateDTO(
                        "Customer Service Representative",
                        "Nhân viên CSKH",
                        2,
                        1
                ),

                new PositionAddUpdateDTO(
                        "Customer Support Specialist",
                        "Chuyên viên hỗ trợ khách hàng",
                        2,
                        1
                ),

                new PositionAddUpdateDTO(
                        "Customer Service Supervisor",
                        "Giám sát CSKH",
                        2,
                        1
                ),

                // DepartmentId = 3 (Directors)
                // Manager/director positions are intentionally excluded.

                // DepartmentId = 4 (Finance)
                new PositionAddUpdateDTO(
                        "Financial Analyst",
                        "Chuyên viên phân tích tài chính",
                        4,
                        1
                ),

                new PositionAddUpdateDTO(
                        "Business Analyst",
                        "Chuyên viên phân tích kinh doanh",
                        4,
                        1
                ),

                new PositionAddUpdateDTO(
                        "Treasury Specialist",
                        "Chuyên viên quản lý quỹ",
                        4,
                        1
                ),

                new PositionAddUpdateDTO(
                        "Finance Assistant",
                        "Trợ lý tài chính",
                        4,
                        1
                ),

                // DepartmentId = 5 (Human Resources)
                new PositionAddUpdateDTO(
                        "HR Specialist",
                        "Chuyên viên nhân sự",
                        5,
                        1
                ),

                new PositionAddUpdateDTO(
                        "Recruitment Officer",
                        "Chuyên viên tuyển dụng",
                        5,
                        1
                ),

                new PositionAddUpdateDTO(
                        "Training Specialist",
                        "Chuyên viên đào tạo",
                        5,
                        1
                ),
                new PositionAddUpdateDTO(
                        "HR Assistant",
                        "Trợ lý nhân sự",
                        5,
                        1
                ),

                // DepartmentId = 6 (Information Technology)
                new PositionAddUpdateDTO(
                        "Software Developer",
                        "Lập trình viên",
                        6,
                        1
                ),

                new PositionAddUpdateDTO(
                        "System Administrator",
                        "Quản trị hệ thống",
                        6,
                        1
                ),

                new PositionAddUpdateDTO(
                        "IT Support",
                        "Hỗ trợ CNTT",
                        6,
                        1
                ),

                new PositionAddUpdateDTO(
                        "Network Engineer",
                        "Kỹ sư mạng",
                        6,
                        1
                ),

                new PositionAddUpdateDTO(
                        "Database Administrator",
                        "Quản trị cơ sở dữ liệu",
                        6,
                        1
                ),

                new PositionAddUpdateDTO(
                        "Tester",
                        "Nhân viên kiểm thử",
                        6,
                        1
                ),

                new PositionAddUpdateDTO(
                        "Business Analyst",
                        "Chuyên viên phân tích CNTT",
                        6,
                        1
                ),

                // DepartmentId = 7 (Legal)
                new PositionAddUpdateDTO(
                        "Legal Advisor",
                        "Cố vấn pháp lý",
                        7,
                        1
                ),

                new PositionAddUpdateDTO(
                        "Legal Assistant",
                        "Trợ lý pháp lý",
                        7,
                        1
                ),

                new PositionAddUpdateDTO(
                        "Compliance Officer",
                        "Nhân viên tuân thủ",
                        7,
                        1
                ),

                new PositionAddUpdateDTO(
                        "Paralegal",
                        "Nhân viên pháp lý",
                        7,
                        1
                ),

                // DepartmentId = 8 (Marketing)
                new PositionAddUpdateDTO(
                        "Marketing Specialist",
                        "Chuyên viên marketing",
                        8,
                        1
                ),

                new PositionAddUpdateDTO(
                        "Marketing Assistant",
                        "Trợ lý marketing",
                        8,
                        1
                ),

                new PositionAddUpdateDTO(
                        "Content Creator",
                        "Nhân viên nội dung",
                        8,
                        1
                ),

                new PositionAddUpdateDTO(
                        "Social Media Specialist",
                        "Chuyên viên mạng xã hội",
                        8,
                        1
                ),

                // DepartmentId = 9 (Operations)
                new PositionAddUpdateDTO(
                        "Operations Officer",
                        "Nhân viên vận hành",
                        9,
                        1
                ),

                new PositionAddUpdateDTO(
                        "Operations Coordinator",
                        "Điều phối viên vận hành",
                        9,
                        1
                ),

                new PositionAddUpdateDTO(
                        "Logistics Specialist",
                        "Chuyên viên logistics",
                        9,
                        1
                ),

                // DepartmentId = 10 (Research & Development)
                new PositionAddUpdateDTO(
                        "R&D Specialist",
                        "Chuyên viên nghiên cứu & phát triển",
                        10,
                        1
                ),

                new PositionAddUpdateDTO(
                        "R&D Assistant",
                        "Trợ lý R&D",
                        10,
                        1
                ),

                new PositionAddUpdateDTO(
                        "Product Developer",
                        "Nhân viên phát triển sản phẩm",
                        10,
                        1
                ),

                new PositionAddUpdateDTO(
                        "Innovation Specialist",
                        "Chuyên viên sáng tạo",
                        10,
                        1
                ),

                // DepartmentId = 11 (Sales)
                new PositionAddUpdateDTO(
                        "Sales Executive",
                        "Nhân viên bán hàng",
                        11,
                        1
                ),

                new PositionAddUpdateDTO(
                        "Senior Sales Executive",
                        "Nhân viên bán hàng cao cấp",
                        11,
                        1
                ),

                new PositionAddUpdateDTO(
                        "Sales Assistant",
                        "Trợ lý bán hàng",
                        11,
                        1
                ),

                new PositionAddUpdateDTO(
                        "Key Account Manager",
                        "Quản lý khách hàng chính",
                        11,
                        1
                ),

                new PositionAddUpdateDTO(
                        "Sales Coordinator",
                        "Điều phối viên bán hàng",
                        11,
                        1
                ),

                // DepartmentId = 12 (Security)
                new PositionAddUpdateDTO(
                        "Security Guard",
                        "Bảo vệ",
                        12,
                        1
                ),

                new PositionAddUpdateDTO(
                        "Security Supervisor",
                        "Giám sát bảo vệ",
                        12,
                        1
                ),

                new PositionAddUpdateDTO(
                        "Security Coordinator",
                        "Điều phối viên bảo vệ",
                        12,
                        1
                )

        );
    }

    @Override
    public void run(String... args) {

        // Equivalent to:
        // if (Positions.Where(x => x.PositionCategory.Id == 1).Any())
        //     return;

        if (positionRepository.existsByPositionCategoryId(1)) {
            System.out.println(
                    "Employee positions already seeded. Skipping."
            );
            return;
        }

        List<PositionAddUpdateDTO> listDTO =
                getPositionAddUpdateDTOs();

        for (PositionAddUpdateDTO dto : listDTO) {

            Department department =
                    departmentRepository.findById(dto.getDepartmentId())
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Department not found with id = "
                                                    + dto.getDepartmentId()
                                    )
                            );

            PositionCategory positionCategory =
                    positionCategoryRepository.findById(
                                    dto.getPositionCategoryId())
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "PositionCategory not found with id = "
                                                    + dto.getPositionCategoryId()
                                    )
                            );

            Position position = new Position();

            position.setEnglishName(dto.getEnglishName());
            position.setVietnameseName(dto.getVietnameseName());
            position.setDepartment(department);
            position.setPositionCategory(positionCategory);

            positionRepository.save(position);
        }

        System.out.println(
                "Employee positions seeded successfully. Total = "
                        + listDTO.size()
        );
    }
}

