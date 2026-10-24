package JavaAPI_DUC_Company1.mapper;

import JavaAPI_DUC_Company1.model.Book;
import JavaAPI_DUC_Company1.model.dto.book.BookAddUpdateDTO;
import JavaAPI_DUC_Company1.model.dto.book.BookOutputSearchDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    //ignore = true because a new item does not need id to be created .
    @Mapping(target = "id", ignore = true)
    //ignore = true because MapStruct cannot convert
    // from Integer (bookCategoryId) to BookCategory
    @Mapping(target = "bookCategory", ignore = true)
    Book toEntity(BookAddUpdateDTO modelDTO);

    @Mapping(target = "bookCategory", ignore = true)
    void updateEntity(BookAddUpdateDTO modelDTO, @MappingTarget Book entity);

    @Mapping(source = "bookCategory.id", target = "bookCategoryId")
    @Mapping(source = "bookCategory.name", target = "bookCategoryName")
    BookOutputSearchDTO toOutputSearchDTO(Book book);

    List<BookOutputSearchDTO> toOutputSearchDTOList(List<Book> books);
}