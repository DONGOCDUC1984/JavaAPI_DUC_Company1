package JavaAPI_DUC_Company1.service.business.interfaces;

import JavaAPI_DUC_Company1.model.Book;
import JavaAPI_DUC_Company1.model.dto.book.BookAddUpdateDTO;
import JavaAPI_DUC_Company1.model.dto.book.BookInputSearchDTO;
import JavaAPI_DUC_Company1.model.dto.book.BookOutputSearchDTO;
import JavaAPI_DUC_Company1.model.paginatedlist.PaginatedListModel;
import java.io.ByteArrayInputStream;
import java.util.List;

public interface IBookService {
    PaginatedListModel<BookOutputSearchDTO> search(
            BookInputSearchDTO input);
    Book getById(Integer id);
    void addUpdate(BookAddUpdateDTO modelDTO);
    void delete(List<Integer> ids);
    ByteArrayInputStream exportToExcel(
            BookInputSearchDTO input) throws Exception;
}
