package JavaAPI_DUC_Company1.service.business.interfaces;

import JavaAPI_DUC_Company1.model.SalesOrder;
import JavaAPI_DUC_Company1.model.dto.salesOrder.SalesOrderAddUpdateDTO;
import JavaAPI_DUC_Company1.model.dto.salesOrder.SalesOrderReadDTO;

import java.util.List;

public interface ISalesOrderService {
    List<SalesOrderReadDTO> getAll();
    List<SalesOrderReadDTO> getByUserId();
    SalesOrder getById(Integer id);
    void add(SalesOrderAddUpdateDTO modelDTO);
//    void delete(Integer id);
    void markDelivered(Integer id);
    void markCancelled(Integer id);
}

