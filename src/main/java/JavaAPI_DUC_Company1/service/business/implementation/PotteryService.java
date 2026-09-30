package JavaAPI_DUC_Company1.service.business.implementation;

import JavaAPI_DUC_Company1.mapper.PotteryMapper;
import JavaAPI_DUC_Company1.model.Pottery;
import JavaAPI_DUC_Company1.model.dto.pottery.PotteryAddUpdateDTO;
import JavaAPI_DUC_Company1.model.dto.pottery.PotteryInputSearchDTO;
import JavaAPI_DUC_Company1.model.dto.pottery.PotteryOutputSearchDTO;
import JavaAPI_DUC_Company1.model.paginatedlist.PaginatedListModel;
import JavaAPI_DUC_Company1.repository.PotteryRepository;
import JavaAPI_DUC_Company1.service.business.interfaces.IPotteryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.io.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import static JavaAPI_DUC_Company1.service.common.ExcelHelper.createHeaders;
import static JavaAPI_DUC_Company1.service.common.FormatHelper.DATE_FORMAT;

@Service
@Transactional
//MyBatis for to search , filter with over 1,000,000 potteries
//This Service utilizes both MyBatis and JPA
public class PotteryService implements IPotteryService {
    private final PotteryRepository _repo;
    private final PotteryMapper _mapper;
    public PotteryService(PotteryRepository repo, PotteryMapper mapper)
    {
        _repo= repo;
        _mapper = mapper;
    }

    @Override
    public void saveBatch(List<Pottery> potteries)
    {
        _repo.saveAll(potteries);
    }

    //    Method by MyBatis
    @Override
    @Transactional(readOnly = true)
    public PaginatedListModel<PotteryOutputSearchDTO> search(
            PotteryInputSearchDTO input)
    {
        // Validate page
        if (input.getCurrentPage() < 1) {
            input.setCurrentPage(1);
        }

        if (input.getPageSize() < 1) {
            input.setPageSize(100);
        }

        // Get current page
        List<PotteryOutputSearchDTO> items = _mapper.search(input);

        // Get total number of matching records
        Long countResult = _mapper.searchCount(input);
        long count = countResult == null ? 0 : countResult;

        // Calculate total pages
        int totalPages = (int) Math.ceil((double) count / input.getPageSize());

        // Build response
        PaginatedListModel<PotteryOutputSearchDTO> result = new PaginatedListModel<>();

        result.setItems(items);
        result.setCount(count);
        result.setCurrentPage(input.getCurrentPage());
        result.setPageSize(input.getPageSize());
        result.setTotalPages(totalPages);

        return result;
    }

    //    Method by MyBatis
    @Override
    public void addUpdate(PotteryAddUpdateDTO modelDTO) {
        //Add or Create
        if (modelDTO.getId()==null || modelDTO.getId()==0){
           _mapper.create(modelDTO);
        }
        //Update
        else {
            _mapper.update(modelDTO);
        }
    }

    //    Delete method by MyBatis
//    @Override
//    public void delete(List<Integer> ids) {
//        for (Integer id : ids) {
//            _mapper.delete(id);
//        }
//    }

    // The above delete method by MyBatis can be replaced
    // by the following delete method by JPA
    //Delete method by JPA
    @Override
    public void delete(List<Integer> ids) {
        for (int i = 0; i < ids.size(); i++) {
            _repo.deleteById(ids.get(i));
        }
    }

    private void writeRows(Sheet sheet, int rowIndex, PotteryOutputSearchDTO dto)
    {
        Row row = sheet.createRow(rowIndex);
        row.createCell(0).setCellValue(dto.getId());
        row.createCell(1).setCellValue(dto.getName());
        row.createCell(2).setCellValue(dto.getColour());
        row.createCell(3).setCellValue(
                Boolean.TRUE.equals(dto.getIsMadeInVietnam())
                        ? "Yes" : "No");
        row.createCell(4).setCellValue(dto.getPrice().doubleValue());
        row.createCell(5).setCellValue(dto.getPotteryCategoryName());
        row.createCell(6).setCellValue(dto.getManufacturingDate()
                        .format(DATE_FORMAT));
    }
//     It takes about
//     _6 seconds to export 66,000 potteries
//     _23 seconds to export 333,000 potteries
//     _48 seconds to export 1,000,000 potteries
    @Override
    @Transactional(readOnly = true)
    public ByteArrayInputStream exportToExcel(
            PotteryInputSearchDTO input) throws Exception {

        // 1. Create streaming Excel workbook
        SXSSFWorkbook workbook = new SXSSFWorkbook(500);
        Sheet sheet = workbook.createSheet("Potteries");

        String[] headers = {"Id", "Name", "Colour", "Is Made In Vietnam?", "Price",
                "Category", "Manufacturing Date"
        };
        // These are the widths of the columns with the above header's name
        // :"Id", "Name", "Colour", "Is Made In Vietnam?", "Price",...
        Integer[] widths = {2500, 11000, 2500, 5500, 2000, 3500, 5000};

        createHeaders(workbook, sheet, headers, widths);
        // 2. Export configuration
        int rowIndex = 1;
        final int batchSize = 5000;
        int lastId = 0;
        // 3. Read data batch by batch using MyBatis
        while (true) {
            long queryStart = System.currentTimeMillis();
            List<PotteryOutputSearchDTO> rows =
                    _mapper.export(lastId, batchSize, input);

            // No more data
            if (rows.isEmpty()) {
                break;
            }

            // 4. Write this batch to Excel
            for (PotteryOutputSearchDTO dto : rows) {
                writeRows(sheet, rowIndex++, dto);

                // Important:
                // The next database batch starts after this ID.
                lastId = dto.getId();
            }

        }

        // 5. Write the workbook into memory
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        workbook.write(output);

        // 6. Release SXSSF temporary files/resources
        workbook.dispose();
        workbook.close();

        // 7. Return Excel file
        return new ByteArrayInputStream(output.toByteArray());
    }

}
