package JavaAPI_DUC_Company1.service.business.implementation;

import JavaAPI_DUC_Company1.mapper.BookMapper;
import JavaAPI_DUC_Company1.model.Book;
import JavaAPI_DUC_Company1.model.BookCategory;
import JavaAPI_DUC_Company1.model.dto.book.BookAddUpdateDTO;
import JavaAPI_DUC_Company1.model.dto.book.BookInputSearchDTO;
import JavaAPI_DUC_Company1.model.dto.book.BookOutputSearchDTO;
import JavaAPI_DUC_Company1.model.paginatedlist.PaginatedListModel;
import JavaAPI_DUC_Company1.repository.BookCategoryRepository;
import JavaAPI_DUC_Company1.repository.BookRepository;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository _repo;

    @Mock
    private BookCategoryRepository _bookCategoryRepo;

    @Mock
    private BookMapper _mapper;

    private BookService _service;

    @BeforeEach
    void setUp() {
        _service = new BookService(
                _repo,
                _bookCategoryRepo,
                _mapper
        );
    }

    // search()
    @Test
    void search_shouldReturnPaginatedBooks() {
        // Arrange
        BookInputSearchDTO input = new BookInputSearchDTO();
        input.setCurrentPage(2);
        input.setPageSize(10);

        Book book1 = new Book();
        book1.setId(11);

        Book book2 = new Book();
        book2.setId(12);

        List<Book> books = List.of(book1, book2);

        Page<Book> page = new PageImpl<>(
                books,
                PageRequest.of(
                        1,
                        10,
                        Sort.by("id")
                ),
                25
        );

        BookOutputSearchDTO dto1 = new BookOutputSearchDTO();
        dto1.setId(11);

        BookOutputSearchDTO dto2 = new BookOutputSearchDTO();
        dto2.setId(12);

        List<BookOutputSearchDTO> dtoList = List.of(dto1, dto2);

        when(_repo.findAll(
                any(Specification.class),
                any(Pageable.class)
        )).thenReturn(page);

        when(_mapper.toOutputSearchDTOList(books)).thenReturn(dtoList);

        // Act
        PaginatedListModel<BookOutputSearchDTO> result = _service.search(input);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getItems().size());
        assertEquals(25, result.getCount());
        assertEquals(2, result.getCurrentPage());
        assertEquals(10, result.getPageSize());
        assertEquals(3, result.getTotalPages());
        assertEquals(11, result.getItems().get(0).getId());
        assertEquals(12, result.getItems().get(1).getId());

        verify(_repo, times(1))
                .findAll(
                        any(Specification.class),
                        any(Pageable.class)
                );

        verify(_mapper, times(1))
                .toOutputSearchDTOList(books);
    }


    @Test
    void search_shouldUseCorrectPageAndPageSize() {
        // Arrange
        BookInputSearchDTO input = new BookInputSearchDTO();
        input.setCurrentPage(3);
        input.setPageSize(20);

        Page<Book> page = new PageImpl<>(
                List.of(),
                PageRequest.of(2, 20, Sort.by("id")), 0
        );

        when(_repo.findAll(
                any(Specification.class),
                any(Pageable.class)
        )).thenReturn(page);

        when(_mapper.toOutputSearchDTOList(anyList())).thenReturn(List.of());

        // Act
        _service.search(input);

        // Assert
        ArgumentCaptor<Pageable> pageableCaptor =
                ArgumentCaptor.forClass(Pageable.class);

        verify(_repo).findAll(
                any(Specification.class),
                pageableCaptor.capture()
        );

        Pageable pageable = pageableCaptor.getValue();
        assertEquals(2, pageable.getPageNumber());
        assertEquals(20, pageable.getPageSize());
        assertEquals(Sort.by("id"), pageable.getSort());
    }

    // getById()

    @Test
    void getById_shouldReturnBook_whenBookExists() {
        // Arrange
        Integer id = 1;

        Book book = new Book();
        book.setId(id);
        book.setName("Java Book");

        when(_repo.findById(id)).thenReturn(Optional.of(book));

        // Act
        Book result = _service.getById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Java Book", result.getName());

        verify(_repo, times(1)).findById(id);
    }


    @Test
    void getById_shouldThrowException_whenBookDoesNotExist() {
        // Arrange
        Integer id = 999;
        when(_repo.findById(id)).thenReturn(Optional.empty());

        // Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                        () -> _service.getById(id));

        assertEquals("Book not found with id 999", exception.getMessage());

        verify(_repo, times(1)).findById(id);
    }

    // addUpdate() - CREATE

    @Test
    void addUpdate_shouldCreateNewBook_whenIdIsNull() {
        // Arrange
        BookAddUpdateDTO modelDTO = new BookAddUpdateDTO();
        modelDTO.setId(null);
        modelDTO.setBookCategoryId(5);

        BookCategory category = new BookCategory();
        category.setId(5);

        Book book = new Book();
        book.setName("Chair");

        when(_bookCategoryRepo.findById(5)).thenReturn(Optional.of(category));
        when(_mapper.toEntity(modelDTO)).thenReturn(book);


        // Act
        _service.addUpdate(modelDTO);

        // Assert
        verify(_bookCategoryRepo, times(1)).findById(5);
        verify(_mapper, times(1)).toEntity(modelDTO);
        verify(_mapper, never()).updateEntity(any(), any());
        verify(_repo, times(1)).save(book);

        assertSame(category, book.getBookCategory());
    }

    // addUpdate() - CREATE with id = 0

    @Test
    void addUpdate_shouldCreateNewBook_whenIdIsZero() {
        // Arrange
        BookAddUpdateDTO modelDTO = new BookAddUpdateDTO();
        modelDTO.setId(0);
        modelDTO.setBookCategoryId(5);

        BookCategory category = new BookCategory();
        category.setId(5);

        Book book = new Book();
        when(_bookCategoryRepo.findById(5)).thenReturn(Optional.of(category));
        when(_mapper.toEntity(modelDTO)).thenReturn(book);

        // Act
        _service.addUpdate(modelDTO);

        // Assert
        verify(_mapper, times(1)).toEntity(modelDTO);
        verify(_mapper, never()).updateEntity(any(), any());
        verify(_repo, times(1)).save(book);

        assertSame(category, book.getBookCategory());
    }

    // addUpdate() - UPDATE

    @Test
    void addUpdate_shouldUpdateExistingBook_whenIdExists() {

        // Arrange

        Integer id = 10;
        BookAddUpdateDTO modelDTO = new BookAddUpdateDTO();

        modelDTO.setId(id);
        modelDTO.setBookCategoryId(5);

        BookCategory category = new BookCategory();
        category.setId(5);

        Book existingBook = new Book();
        existingBook.setId(id);
        existingBook.setName("Old Name");

        when(_bookCategoryRepo.findById(5)).thenReturn(Optional.of(category));
        when(_repo.findById(id)).thenReturn(Optional.of(existingBook));

        // Act
        _service.addUpdate(modelDTO);

        // Assert

        verify(_bookCategoryRepo, times(1)).findById(5);
        verify(_repo, times(1)).findById(id);
        verify(_mapper, times(1)).updateEntity(modelDTO, existingBook);
        verify(_mapper, never()).toEntity(any());
        verify(_repo, times(1)).save(existingBook);
        assertSame(category, existingBook.getBookCategory());
    }

    // addUpdate() - CATEGORY NOT FOUND

    @Test
    void addUpdate_shouldThrowException_whenBookCategoryDoesNotExist() {
        // Arrange
        BookAddUpdateDTO modelDTO = new BookAddUpdateDTO();
        modelDTO.setId(null);
        modelDTO.setBookCategoryId(999);

        when(_bookCategoryRepo.findById(999)).thenReturn(Optional.empty());

        // Act + Assert

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> _service.addUpdate(modelDTO)
                );

        assertEquals("BookCategory not found", exception.getMessage());

        verify(_bookCategoryRepo, times(1)).findById(999);
        verify(_mapper, never()).toEntity(any());
        verify(_mapper, never()).updateEntity(any(), any());
        verify(_repo, never()).save(any());
    }


    // addUpdate() - UPDATE BOOK NOT FOUND
    @Test
    void addUpdate_shouldThrowException_whenUpdatingNonExistingBook() {
        // Arrange
        Integer id = 999;

        BookAddUpdateDTO modelDTO = new BookAddUpdateDTO();
        modelDTO.setId(id);
        modelDTO.setBookCategoryId(5);

        BookCategory category = new BookCategory();
        category.setId(5);

        when(_bookCategoryRepo.findById(5)).thenReturn(Optional.of(category));
        when(_repo.findById(id)).thenReturn(Optional.empty());


        // Act + Assert

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> _service.addUpdate(modelDTO)
                );

        assertEquals("Book not found with id 999", exception.getMessage());
        verify(_repo, times(1)).findById(id);
        verify(_mapper, never()).updateEntity(any(), any());
        verify(_repo, never()).save(any());
    }


    // delete()
    @Test
    void delete_shouldDeleteAllSpecifiedBooks() {
        // Arrange
        List<Integer> ids = List.of(1, 2, 3);

        // Act
        _service.delete(ids);

        // Assert
        verify(_repo, times(1)).deleteById(1);
        verify(_repo, times(1)).deleteById(2);
        verify(_repo, times(1)).deleteById(3);
        verify(_repo, times(3)).deleteById(anyInt());
    }


    @Test
    void delete_shouldDoNothing_whenIdListIsEmpty() {
        // Arrange
        List<Integer> ids =List.of();

        // Act
        _service.delete(ids);

        // Assert
        verify(_repo, never()).deleteById(anyInt());
    }

    // exportToExcel()

    @Test
    void exportToExcel_shouldCreateExcelFileWithExpectedData()
            throws Exception {
        // Arrange
        BookInputSearchDTO input = new BookInputSearchDTO();
        input.setCurrentPage(1);
        input.setPageSize(100);

        Book book1 = new Book();
        book1.setId(1);

        Book book2 = new Book();
        book2.setId(2);

        List<Book> books = List.of(book1, book2);
        Page<Book> firstPage = new PageImpl<>(books);
        Page<Book> emptyPage = new PageImpl<>(List.of());

        BookOutputSearchDTO dto1 = new BookOutputSearchDTO();
        dto1.setId(1);
        dto1.setName("Java Book");
        dto1.setColour("Red");
        dto1.setIsMadeInVietnam(true);
        dto1.setBookCategoryName("Programming");
        dto1.setPublishedDay(
                LocalDate.of(2026, 8, 20)
        );


        BookOutputSearchDTO dto2 =
                new BookOutputSearchDTO();

        dto2.setId(2);
        dto2.setName("Spring Book");
        dto2.setColour("Blue");
        dto2.setIsMadeInVietnam(false);
        dto2.setBookCategoryName("Technology");
        dto2.setPublishedDay(
                LocalDate.of(2026, 8, 21)
        );


        when(_repo.findAll(
                any(Specification.class),
                any(Pageable.class)
        ))
                .thenReturn(firstPage)
                .thenReturn(emptyPage);


        when(_mapper.toOutputSearchDTOList(books))
                .thenReturn(List.of(dto1, dto2));


        // Act

        var result =
                _service.exportToExcel(input);


        // Assert

        assertNotNull(result);

        byte[] bytes =
                result.readAllBytes();

        assertTrue(bytes.length > 0);


        // Open the generated Excel file

        try (Workbook workbook =
                     new XSSFWorkbook(
                             new ByteArrayInputStream(bytes)
                     )) {

            Sheet sheet =
                    workbook.getSheet("Books");

            assertNotNull(sheet);


            // Header row

            Row header =
                    sheet.getRow(0);

            assertEquals(
                    "Id",
                    header.getCell(0).getStringCellValue()
            );

            assertEquals(
                    "Name",
                    header.getCell(1).getStringCellValue()
            );

            assertEquals(
                    "Colour",
                    header.getCell(2).getStringCellValue()
            );

            assertEquals(
                    "Is Made In Vietnam?",
                    header.getCell(3).getStringCellValue()
            );

            assertEquals(
                    "Category",
                    header.getCell(4).getStringCellValue()
            );

            assertEquals(
                    "Published Day",
                    header.getCell(5).getStringCellValue()
            );


            // First data row

            Row row1 =
                    sheet.getRow(1);

            assertEquals(
                    1,
                    (int) row1.getCell(0).getNumericCellValue()
            );

            assertEquals(
                    "Java Book",
                    row1.getCell(1).getStringCellValue()
            );

            assertEquals(
                    "Red",
                    row1.getCell(2).getStringCellValue()
            );

            assertEquals(
                    "Yes",
                    row1.getCell(3).getStringCellValue()
            );

            assertEquals(
                    "Programming",
                    row1.getCell(4).getStringCellValue()
            );

            assertEquals(
                    "20-08-2026",
                    row1.getCell(5).getStringCellValue()
            );


            // Second data row

            Row row2 =
                    sheet.getRow(2);

            assertEquals(
                    2,
                    (int) row2.getCell(0).getNumericCellValue()
            );

            assertEquals(
                    "Spring Book",
                    row2.getCell(1).getStringCellValue()
            );

            assertEquals(
                    "Blue",
                    row2.getCell(2).getStringCellValue()
            );

            assertEquals(
                    "No",
                    row2.getCell(3).getStringCellValue()
            );

            assertEquals(
                    "Technology",
                    row2.getCell(4).getStringCellValue()
            );

            assertEquals(
                    "21-08-2026",
                    row2.getCell(5).getStringCellValue()
            );
        }


        // Repository should be called twice:
        //
        // 1. First batch -> 2 books
        // 2. Second batch -> empty -> stop

        verify(_repo, times(2))
                .findAll(
                        any(Specification.class),
                        any(Pageable.class)
                );


        verify(_mapper, times(1))
                .toOutputSearchDTOList(books);
    }


    @Test
    void exportToExcel_shouldReturnExcelWithOnlyHeaders_whenNoBooksExist()
            throws Exception {

        // Arrange
        BookInputSearchDTO input = new BookInputSearchDTO();
        input.setCurrentPage(1);
        input.setPageSize(100);

        Page<Book> emptyPage = new PageImpl<>(List.of());
        when(_repo.findAll(
                any(Specification.class),
                any(Pageable.class)
        ))
                .thenReturn(emptyPage);

        // Act
        var result = _service.exportToExcel(input);

        // Assert
        assertNotNull(result);
        byte[] bytes = result.readAllBytes();
        assertTrue(bytes.length > 0);


        try (Workbook workbook =
                     new XSSFWorkbook(
                             new ByteArrayInputStream(bytes)
                     )) {

            Sheet sheet = workbook.getSheet("Books");
            assertNotNull(sheet);
            assertEquals(1, sheet.getPhysicalNumberOfRows());

            Row header = sheet.getRow(0);
            assertEquals(
                    "Id",
                    header.getCell(0).getStringCellValue()
            );

            assertEquals(
                    "Name",
                    header.getCell(1).getStringCellValue()
            );
        }


        verify(_repo, times(1))
                .findAll(
                        any(Specification.class),
                        any(Pageable.class)
                );

        verify(_mapper, never())
                .toOutputSearchDTOList(anyList());
    }
}
