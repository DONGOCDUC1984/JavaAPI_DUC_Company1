package JavaAPI_DUC_Company1.service.business.interfaces;

import JavaAPI_DUC_Company1.model.BookCategory;
import java.util.List;

public interface IBookCategoryService {
    List<BookCategory> getAll();
    BookCategory getById(Integer id);
    BookCategory addUpdate(BookCategory model);
    void delete(Integer id);
}
