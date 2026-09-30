package JavaAPI_DUC_Company1.service.business.interfaces;

import JavaAPI_DUC_Company1.model.Staff;
import JavaAPI_DUC_Company1.model.dto.staff.StaffAddUpdateDTO;
import JavaAPI_DUC_Company1.model.dto.staff.StaffInputSearchDTO;
import JavaAPI_DUC_Company1.model.dto.staff.StaffOutputSearchDTO;
import JavaAPI_DUC_Company1.model.paginatedlist.PaginatedListModel;
import java.io.ByteArrayInputStream;
import java.util.List;

public interface IStaffService {
    PaginatedListModel<StaffOutputSearchDTO> search(
            StaffInputSearchDTO input);
    Staff getById(Integer id);
    void addUpdate(StaffAddUpdateDTO modelDTO);
    void delete(List<Integer> ids);
    ByteArrayInputStream exportToExcel(
            StaffInputSearchDTO input) throws Exception;
}

