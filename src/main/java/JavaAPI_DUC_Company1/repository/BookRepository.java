package JavaAPI_DUC_Company1.repository;

import JavaAPI_DUC_Company1.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository
        extends JpaRepository<Book, Integer> ,
        //JpaSpecificationExecutor is necessary for
        // _repo.search(specification, pageable); in file BookService
        JpaSpecificationExecutor<Book> {
}
