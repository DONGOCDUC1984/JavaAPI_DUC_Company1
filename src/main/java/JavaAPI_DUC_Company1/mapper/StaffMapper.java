package JavaAPI_DUC_Company1.mapper;

import JavaAPI_DUC_Company1.model.Staff;
import JavaAPI_DUC_Company1.model.dto.staff.StaffAddUpdateDTO;
import JavaAPI_DUC_Company1.model.dto.staff.StaffOutputSearchDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring")
public interface StaffMapper {
    //ignore = true because a new item does not need id to be created .
    @Mapping(target = "id", ignore = true)
    //ignore = true because MapStruct cannot convert
    // from Integer (StaffCategoryId) to StaffCategory
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "provinceCity", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "position", ignore = true)
    Staff toEntity(StaffAddUpdateDTO modelDTO);

    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "provinceCity", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "position", ignore = true)
    void updateEntity(StaffAddUpdateDTO modelDTO,
                      @MappingTarget Staff entity);

    @Mapping(source = "provinceCity.id", target = "provinceCityId")
    @Mapping(source = "provinceCity.name", target = "provinceCityName")
    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.englishName", target = "departmentEnglishName")
    @Mapping(source = "department.vietnameseName", target = "departmentVietnameseName")
    @Mapping(source = "position.id", target = "positionId")
    @Mapping(source = "position.englishName", target = "positionEnglishName")
    @Mapping(source = "position.vietnameseName", target = "positionVietnameseName")
    StaffOutputSearchDTO toOutputSearchDTO(Staff staff);

    List<StaffOutputSearchDTO> toOutputSearchDTOList(
            List<Staff> staffs);
}
