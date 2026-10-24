package JavaAPI_DUC_Company1.service.business.interfaces;

import JavaAPI_DUC_Company1.model.FormerStaff;
import JavaAPI_DUC_Company1.model.dto.formerStaff.FormerStaffInputSearchDTO;
import JavaAPI_DUC_Company1.model.dto.formerStaff.FormerStaffOutputSearchDTO;
import JavaAPI_DUC_Company1.model.paginatedlist.PaginatedListModel;

public interface IFormerStaffService {
    PaginatedListModel<FormerStaffOutputSearchDTO> search(
            FormerStaffInputSearchDTO input);
    FormerStaff getById(Integer id);

}


