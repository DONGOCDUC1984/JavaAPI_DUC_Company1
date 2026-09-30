package JavaAPI_DUC_Company1.service.business.implementation;

import JavaAPI_DUC_Company1.mapper.BookMapper;
import JavaAPI_DUC_Company1.model.Book;
import JavaAPI_DUC_Company1.model.BookCategory;
import JavaAPI_DUC_Company1.model.dto.book.BookAddUpdateDTO;
import JavaAPI_DUC_Company1.model.dto.book.BookInputSearchDTO;
import JavaAPI_DUC_Company1.model.dto.book.BookOutputSearchDTO;
import JavaAPI_DUC_Company1.model.paginatedlist.PaginatedListModel;
import JavaAPI_DUC_Company1.repository.BookRepository;
import JavaAPI_DUC_Company1.repository.BookCategoryRepository;
import JavaAPI_DUC_Company1.service.business.interfaces.IBookService;
import JavaAPI_DUC_Company1.specification.BookSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import static JavaAPI_DUC_Company1.service.common.ExcelHelper.createHeaders;
import static JavaAPI_DUC_Company1.service.common.FormatHelper.DATE_FORMAT;

@Service
@Transactional
public class BookService implements IBookService {
    private final BookRepository _repo;
    private final BookCategoryRepository _bookCategoryRepo;
    private final BookMapper _mapper;

    public  BookService(BookRepository repo,
                        BookCategoryRepository bookCategoryRepo,
                        BookMapper mapper){
        _repo=repo;
        _bookCategoryRepo=bookCategoryRepo;
        _mapper = mapper;
    }

    @Override
    // With  @Transactional(readOnly = true),
    // Hibernate knows: "Nothing will be updated.".Therefore it skips most
    // of the dirty-checking work, reducing CPU and memory overhead for
    // read operations.
    @Transactional(readOnly = true)
    public PaginatedListModel<BookOutputSearchDTO> search(
            BookInputSearchDTO input) {
        //    Comparison
        //    C# EF Core	        Spring Boot
        //    IQueryable<Book>	    Specification<Book>
        //    .Where()	            Predicate
        //    .Include()	        @ManyToOne / fetch join if needed
        //    .CountAsync()	        page.getTotalElements()
        //    .Skip().Take()	    PageRequest
        //    .OrderBy()	        Sort.by("id")
        //    ToListAsync()	        Page<Book>
        Specification<Book> specification = BookSpecification.filter(input);

        Pageable pageable =
                PageRequest.of(
                        input.getCurrentPage() - 1,
                        input.getPageSize(),
                        Sort.by("id"));

        Page<Book> page = _repo.findAll(specification, pageable);

        PaginatedListModel<BookOutputSearchDTO> result = new PaginatedListModel<>();

        result.setItems(_mapper.toOutputSearchDTOList(page.getContent()));
        result.setCount(page.getTotalElements());
        result.setCurrentPage(input.getCurrentPage());
        result.setPageSize(input.getPageSize());
        result.setTotalPages(page.getTotalPages());

        return result;
    }

    @Override
    // With  @Transactional(readOnly = true),
    // Hibernate knows: "Nothing will be updated.".Therefore it skips most
    // of the dirty-checking work, reducing CPU and memory overhead for
    // read operations.
    @Transactional(readOnly = true)
    public Book getById(Integer id) {
        return _repo.findById(id)
                .orElseThrow(() ->new RuntimeException("Book not found with id " + id) );
    }

    @Override
    // If "void" is replaced by "Book" in the following line, maybe there will be troubles and errors.
    public void addUpdate(BookAddUpdateDTO modelDTO) {
        BookCategory bookCategory=_bookCategoryRepo
                .findById(modelDTO.getBookCategoryId())
                .orElseThrow(() ->new RuntimeException("BookCategory not found") );
        Book book;
        //Add
        if (modelDTO.getId()==null || modelDTO.getId()==0){
            book=_mapper.toEntity(modelDTO);
        }
        //Update
        else {
            book=getById(modelDTO.getId());
            _mapper.updateEntity(modelDTO,book);
        }
        book.setBookCategory(bookCategory);
        _repo.save(book);
    }

    @Override
    public void delete(List<Integer> ids) {
        for (int i = 0; i < ids.size(); i++) {
            _repo.deleteById(ids.get(i));
        }
    }

    // Write one bookOutputSearchDTO to one Excel row
    private void writeRows(Sheet sheet, int rowIndex, BookOutputSearchDTO modelDTO)
    {
        Row row = sheet.createRow(rowIndex);

        row.createCell(0).setCellValue(modelDTO.getId());
        row.createCell(1).setCellValue(modelDTO.getName());
        row.createCell(2).setCellValue(modelDTO.getColour());
        row.createCell(3)
                .setCellValue(
                        Boolean.TRUE.equals(modelDTO.getIsMadeInVietnam())
                                ? "Yes" : "No");

        row.createCell(4).setCellValue(modelDTO.getBookCategoryName());
        row.createCell(5).setCellValue(modelDTO.getPublishedDay()
                        .format(DATE_FORMAT));
    }

    // Build search specification
    private Specification<Book> buildSpecification(
            BookInputSearchDTO input) {

        return BookSpecification.filter(input);
    }

    // It takes about 7 seconds to export 100000 items to an Excel file.
    @Override
    @Transactional(readOnly = true)
    public ByteArrayInputStream exportToExcel(BookInputSearchDTO input)
//  "throws Exception" means "This method may throw a checked exception..."
//  For instance,
//  workbook.write(output);
//  or
//  workbook.close();
//  can throw IOException
            throws Exception
    {
//        To count time
//        long t1 = System.currentTimeMillis();

        Specification<Book> specification = buildSpecification(input);
        SXSSFWorkbook workbook = new SXSSFWorkbook(500);
        Sheet sheet = workbook.createSheet("Books");
        String[] headerNames ={"Id","Name","Colour", "Is Made In Vietnam?"
                ,"Category","Published Day"};
        // Fixed column widths: These numbers are the widths of columns like
        //"Id","Name","Colour", "Is Made In Vietnam?","Category","Published Day"
        // If I use .autoSizeColumn(...) instead of Fixed column widths,
        // it will be very slow.
        Integer[] headerWidths ={2500,9000,3000,5200,3000,4500};
        createHeaders(workbook,sheet,headerNames,headerWidths);

        int rowIndex = 1;
        Integer lastId = 0;
        //With batchSize = 5000, it will run much swifter than batchSize = 1000
        final int batchSize = 5000;

        while (true) {

            Pageable pageable = PageRequest.of(0, batchSize,
                    Sort.by("id"));
            // With lastId, it will run much swifter than Offset
            Integer finalLastId = lastId;

            Specification<Book> currentSpecification =
                    specification.and(
                            (root, query, builder) ->
                                    builder.greaterThan(root.get("id"), finalLastId));

            Page<Book> page = _repo.findAll(currentSpecification, pageable);
            if (page.isEmpty()) {
                break;
            }
            List<BookOutputSearchDTO> modelDTOs = _mapper.toOutputSearchDTOList(
                    page.getContent());

            for (BookOutputSearchDTO dto : modelDTOs) {
                writeRows(sheet, rowIndex++, dto);
                lastId = dto.getId();
            }

        }

        // To count time
//        long t2 = System.currentTimeMillis();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        workbook.write(output);
        // To count time
//        long t3 = System.currentTimeMillis();
//        System.out.println("Read & Write Rows : " + (t2 - t1));
//        System.out.println("Generate Excel    : " + (t3 - t2));

        workbook.dispose();
        workbook.close();
        return new ByteArrayInputStream(output.toByteArray());

    }

}


