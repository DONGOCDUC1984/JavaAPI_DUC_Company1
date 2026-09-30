package JavaAPI_DUC_Company1.mapper;

import JavaAPI_DUC_Company1.model.FormerStaff;
import JavaAPI_DUC_Company1.model.dto.formerStaff.FormerStaffOutputSearchDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface FormerStaffMapper {
    @Mapping(source = "provinceCity.id", target = "provinceCityId")
    @Mapping(source = "provinceCity.name", target = "provinceCityName")
    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.englishName", target = "departmentEnglishName")
    @Mapping(source = "department.vietnameseName", target = "departmentVietnameseName")
    @Mapping(source = "position.id", target = "positionId")
    @Mapping(source = "position.englishName", target = "positionEnglishName")
    @Mapping(source = "position.vietnameseName", target = "positionVietnameseName")
    FormerStaffOutputSearchDTO toOutputSearchDTO(FormerStaff Formerstaff);

    List<FormerStaffOutputSearchDTO> toOutputSearchDTOList(
            List<FormerStaff> formerStaffs);
}
