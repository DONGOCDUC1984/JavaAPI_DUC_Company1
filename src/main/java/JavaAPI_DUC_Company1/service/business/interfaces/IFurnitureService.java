package JavaAPI_DUC_Company1.service.business.interfaces;

import JavaAPI_DUC_Company1.model.Furniture;
import JavaAPI_DUC_Company1.model.dto.furniture.FurnitureAddUpdateDTO;
import JavaAPI_DUC_Company1.model.dto.furniture.FurnitureInputSearchDTO;
import JavaAPI_DUC_Company1.model.dto.furniture.FurnitureOutputSearchDTO;
import JavaAPI_DUC_Company1.model.paginatedlist.PaginatedListModel;
import java.io.ByteArrayInputStream;
import java.util.List;

public interface IFurnitureService {
    void saveBatch(List<Furniture> furnitures);
    PaginatedListModel<FurnitureOutputSearchDTO> search(FurnitureInputSearchDTO input);
    void addUpdate(FurnitureAddUpdateDTO modelDTO);
    void delete(List<Integer> ids);
//    ByteArrayInputStream exportToExcel(
//            FurnitureInputSearchDTO input) throws Exception;
}

