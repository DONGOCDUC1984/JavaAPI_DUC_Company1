package JavaAPI_DUC_Company1.mapper;

import JavaAPI_DUC_Company1.model.dto.pottery.PotteryAddUpdateDTO;
import JavaAPI_DUC_Company1.model.dto.pottery.PotteryInputSearchDTO;
import JavaAPI_DUC_Company1.model.dto.pottery.PotteryOutputSearchDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PotteryMapper {
    List<PotteryOutputSearchDTO> search(PotteryInputSearchDTO input);
    Long searchCount(PotteryInputSearchDTO input);
    void create(PotteryAddUpdateDTO modelDTO);
    void update(PotteryAddUpdateDTO modelDTO);
    void delete(Integer id);
    List<PotteryOutputSearchDTO> export(Integer lastId,Integer batchSize, PotteryInputSearchDTO input);
}
