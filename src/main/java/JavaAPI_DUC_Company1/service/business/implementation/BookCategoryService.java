package JavaAPI_DUC_Company1.service.business.implementation;

import JavaAPI_DUC_Company1.model.BookCategory;
import JavaAPI_DUC_Company1.repository.BookCategoryRepository;
import JavaAPI_DUC_Company1.service.business.interfaces.IBookCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookCategoryService implements IBookCategoryService {
    private final BookCategoryRepository _repo;
    public  BookCategoryService(BookCategoryRepository repo){
        _repo=repo;
    }

    @Override
    public List<BookCategory> getAll() {
        return _repo.findAll();
    }

    @Override
    public BookCategory getById(Integer id) {
        return _repo.findById(id)
                .orElseThrow(()
                   -> new RuntimeException("BookCategory not found with id " + id));
    }

    @Override
    public BookCategory addUpdate(BookCategory model) {
        //Add
        if (model.getId()==null || model.getId()==0){
            BookCategory newModel =new BookCategory();
            newModel.setName(model.getName());
            return _repo.save(newModel);
        }
        //Update
        else {
            BookCategory existing=getById(model.getId());
            existing.setName(model.getName());
            return _repo.save(existing);
        }
    }
    @Override
    public void delete(Integer id) {
        _repo.deleteById(id);
    }
}
