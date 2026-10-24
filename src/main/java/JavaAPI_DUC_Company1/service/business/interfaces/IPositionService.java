package JavaAPI_DUC_Company1.service.business.interfaces;

import JavaAPI_DUC_Company1.model.Position;
import JavaAPI_DUC_Company1.model.dto.position.PositionAddUpdateDTO;
import JavaAPI_DUC_Company1.model.dto.position.PositionOutputSearchDTO;

import java.util.List;

public interface IPositionService {
    List<PositionOutputSearchDTO> getAll();
    Position getById(Integer id);
    Position addUpdate(PositionAddUpdateDTO modelDTO);
    void delete(Integer id);
}


