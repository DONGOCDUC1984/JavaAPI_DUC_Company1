package JavaAPI_DUC_Company1.service.business.interfaces;

import JavaAPI_DUC_Company1.model.Pottery;
import JavaAPI_DUC_Company1.model.dto.pottery.PotteryAddUpdateDTO;
import JavaAPI_DUC_Company1.model.dto.pottery.PotteryInputSearchDTO;
import JavaAPI_DUC_Company1.model.dto.pottery.PotteryOutputSearchDTO;
import JavaAPI_DUC_Company1.model.paginatedlist.PaginatedListModel;
import java.io.ByteArrayInputStream;
import java.util.List;

public interface IPotteryService {
    void saveBatch(List<Pottery> potteries);
    PaginatedListModel<PotteryOutputSearchDTO> search(PotteryInputSearchDTO input);
    void addUpdate(PotteryAddUpdateDTO modelDTO);
    void delete(List<Integer> ids);
    ByteArrayInputStream exportToExcel(
            PotteryInputSearchDTO input) throws Exception;
}
