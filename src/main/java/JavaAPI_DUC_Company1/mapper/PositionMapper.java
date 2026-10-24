package JavaAPI_DUC_Company1.mapper;

import JavaAPI_DUC_Company1.model.Position;
import JavaAPI_DUC_Company1.model.dto.position.PositionAddUpdateDTO;
import JavaAPI_DUC_Company1.model.dto.position.PositionOutputSearchDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PositionMapper {
    //ignore = true because a new item does not need id to be created .
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "department", ignore = true)
    //ignore = true because MapStruct cannot convert
    // from Integer (positionCategoryId) to PositionCategory
    @Mapping(target = "positionCategory", ignore = true)
    Position toEntity(PositionAddUpdateDTO modelDTO);

    @Mapping(target = "department", ignore = true)
    @Mapping(target = "positionCategory", ignore = true)
    void updateEntity(PositionAddUpdateDTO modelDTO,
                      @MappingTarget Position entity);

    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.englishName", target = "departmentEnglishName")
    @Mapping(source = "department.vietnameseName", target = "departmentVietnameseName")
    @Mapping(source = "positionCategory.id", target = "positionCategoryId")
    @Mapping(source = "positionCategory.englishName", target = "positionCategoryEnglishName")
    @Mapping(source = "positionCategory.vietnameseName", target = "positionCategoryVietnameseName")
    PositionOutputSearchDTO toOutputSearchDTO(Position position);

    List<PositionOutputSearchDTO> toOutputSearchDTOList(List<Position> positions);
}