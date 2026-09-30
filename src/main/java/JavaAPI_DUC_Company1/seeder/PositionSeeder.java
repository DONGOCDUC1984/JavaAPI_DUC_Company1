package JavaAPI_DUC_Company1.seeder;

import JavaAPI_DUC_Company1.model.Department;
import JavaAPI_DUC_Company1.model.Position;
import JavaAPI_DUC_Company1.model.PositionCategory;
import JavaAPI_DUC_Company1.model.dto.position.PositionAddUpdateDTO;
import JavaAPI_DUC_Company1.repository.DepartmentRepository;
import JavaAPI_DUC_Company1.repository.PositionCategoryRepository;
import JavaAPI_DUC_Company1.repository.PositionRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class PositionSeeder {

    private final PositionRepository positionRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionCategoryRepository positionCategoryRepository;

    public PositionSeeder(
            PositionRepository positionRepository,
            DepartmentRepository departmentRepository,
            PositionCategoryRepository positionCategoryRepository) {

        this.positionRepository = positionRepository;
        this.departmentRepository = departmentRepository;
        this.positionCategoryRepository = positionCategoryRepository;
    }

    private List<PositionAddUpdateDTO> getPositionAddUpdateDTOs() {

        return List.of(

                // DepartmentId = 1 (Accounting)
                createDTO(
                        "Senior Accountant",
                        "Kế toán trưởng",
                        1,
                        2),

                createDTO(
                        "Chief Accountant",
                        "Kế toán trưởng cấp cao",
                        1,
                        2),

                // DepartmentId = 2 (Customer Service)
                createDTO(
                        "Customer Service Manager",
                        "Trưởng phòng CSKH",
                        2,
                        2),

                // DepartmentId = 3 (Directors)
                createDTO(
                        "Chief Executive Officer",
                        "Giám đốc điều hành",
                        3,
                        2),

                createDTO(
                        "Chief Operating Officer",
                        "Giám đốc vận hành",
                        3,
                        2),

                createDTO(
                        "Chief Financial Officer",
                        "Giám đốc tài chính",
                        3,
                        2),

                // DepartmentId = 4 (Finance)
                createDTO(
                        "Finance Manager",
                        "Trưởng phòng tài chính",
                        4,
                        2),

                // DepartmentId = 5 (Human Resources)
                createDTO(
                        "HR Manager",
                        "Trưởng phòng nhân sự",
                        5,
                        2),

                // DepartmentId = 6 (Information Technology)
                createDTO(
                        "IT Manager",
                        "Trưởng phòng CNTT",
                        6,
                        2),

                // DepartmentId = 7 (Legal)
                createDTO(
                        "Legal Manager",
                        "Trưởng phòng pháp chế",
                        7,
                        2),

                // DepartmentId = 8 (Marketing)
                createDTO(
                        "Marketing Manager",
                        "Trưởng phòng marketing",
                        8,
                        2),

                // DepartmentId = 9 (Operations)
                createDTO(
                        "Operations Manager",
                        "Trưởng phòng vận hành",
                        9,
                        2),

                createDTO(
                        "Warehouse Manager",
                        "Trưởng kho",
                        9,
                        2),

                // DepartmentId = 10 (Research & Development)
                createDTO(
                        "R&D Manager",
                        "Trưởng phòng R&D",
                        10,
                        2),

                // DepartmentId = 11 (Sales)
                createDTO(
                        "Sales Manager",
                        "Trưởng phòng bán hàng",
                        11,
                        2),

                // DepartmentId = 12 (Security)
                createDTO(
                        "Chief Security Officer",
                        "Trưởng phòng bảo vệ",
                        12,
                        2)
        );
    }

    private PositionAddUpdateDTO createDTO(
            String englishName,
            String vietnameseName,
            Integer departmentId,
            Integer positionCategoryId) {

        PositionAddUpdateDTO dto = new PositionAddUpdateDTO();

        dto.setEnglishName(englishName);
        dto.setVietnameseName(vietnameseName);
        dto.setDepartmentId(departmentId);
        dto.setPositionCategoryId(positionCategoryId);

        return dto;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void seedPositions() {

        /*
         * PositionCategoryId = 2
         *
         * If positions belonging to category 2 already exist,
         * do not seed them again.
         */
        if (positionRepository.existsByPositionCategoryId(2)) {
            System.out.println(
                    "Positions already exist. Skipping PositionSeeder."
            );
            return;
        }

        List<PositionAddUpdateDTO> listDTO =
                getPositionAddUpdateDTOs();

        for (PositionAddUpdateDTO dto : listDTO) {

            Department department =
                    departmentRepository.findById(
                            dto.getDepartmentId()
                    ).orElseThrow(() ->
                            new RuntimeException(
                                    "Department not found with id = "
                                            + dto.getDepartmentId()
                            ));

            PositionCategory positionCategory =
                    positionCategoryRepository.findById(
                            dto.getPositionCategoryId()
                    ).orElseThrow(() ->
                            new RuntimeException(
                                    "PositionCategory not found with id = "
                                            + dto.getPositionCategoryId()
                            ));

            Position position = new Position(
                    dto.getEnglishName(),
                    dto.getVietnameseName(),
                    positionCategory,
                    department
            );

            positionRepository.save(position);
        }

        System.out.println(
                "PositionSeeder finished. Inserted " + listDTO.size() + " positions."
        );
    }


}

