package JavaAPI_DUC_Company1.service.business.implementation;

import JavaAPI_DUC_Company1.mapper.StaffMapper;
import JavaAPI_DUC_Company1.model.*;
import JavaAPI_DUC_Company1.model.dto.staff.StaffAddUpdateDTO;
import JavaAPI_DUC_Company1.model.dto.staff.StaffInputSearchDTO;
import JavaAPI_DUC_Company1.model.dto.staff.StaffOutputSearchDTO;
import JavaAPI_DUC_Company1.model.paginatedlist.PaginatedListModel;
import JavaAPI_DUC_Company1.repository.DepartmentRepository;
import JavaAPI_DUC_Company1.repository.PositionRepository;
import JavaAPI_DUC_Company1.repository.ProvinceCityRepository;
import JavaAPI_DUC_Company1.repository.StaffRepository;
import JavaAPI_DUC_Company1.service.business.interfaces.IStaffService;
import JavaAPI_DUC_Company1.specification.StaffSpecification;
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
public class StaffService implements IStaffService {
    private final StaffRepository _repo;
    private final ProvinceCityRepository _provinceCityRepo;
    private final DepartmentRepository _departmentRepo;
    private final PositionRepository _positionRepo;
    private final StaffMapper _mapper;

    public  StaffService(StaffRepository repo, ProvinceCityRepository provinceCityRepo,
                         DepartmentRepository departmentRepo, PositionRepository positionRepo,
                         StaffMapper mapper){
        _repo=repo;
        _provinceCityRepo = provinceCityRepo;
        _departmentRepo = departmentRepo;
        _positionRepo = positionRepo;
        _mapper = mapper;
    }

    @Override
    // With  @Transactional(readOnly = true),
    // Hibernate knows: "Nothing will be updated.".Therefore it skips most
    // of the dirty-checking work, reducing CPU and memory overhead for
    // read operations.
    @Transactional(readOnly = true)
    public PaginatedListModel<StaffOutputSearchDTO> search(
            StaffInputSearchDTO input) {
        //    Comparison
        //    C# EF Core	        Spring Boot
        //    IQueryable<Staff>	    Specification<Staff>
        //    .Where()	            Predicate
        //    .Include()	        @ManyToOne / fetch join if needed
        //    .CountAsync()	        page.getTotalElements()
        //    .Skip().Take()	    PageRequest
        //    .OrderBy()	        Sort.by("id")
        //    ToListAsync()	        Page<Staff>
        Specification<Staff> specification =
                StaffSpecification.filter(input);

        Pageable pageable =
                PageRequest.of(
                        input.getCurrentPage() - 1,
                        input.getPageSize(),
                        Sort.by("id"));

        Page<Staff> page = _repo.findAll(specification, pageable);

        PaginatedListModel<StaffOutputSearchDTO> result = new PaginatedListModel<>();

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
    public Staff getById(Integer id) {
        return _repo.findById(id).orElseThrow(() ->new RuntimeException("Staff not found with id " + id) );
    }

    @Override
    // If "void" is replaced by "Staff" in the following line, maybe there will be troubles and errors.
    public void addUpdate(StaffAddUpdateDTO modelDTO) {
        ProvinceCity provinceCity=_provinceCityRepo
                .findById(modelDTO.getProvinceCityId())
                .orElseThrow(() ->new RuntimeException("ProvinceCity not found"));
        Department department=_departmentRepo
                .findById(modelDTO.getDepartmentId())
                .orElseThrow(() ->new RuntimeException("Department not found"));
        Position position=_positionRepo
                .findById(modelDTO.getPositionId())
                .orElseThrow(() ->new RuntimeException("Position not found"));
        Staff staff;
        //Add
        if (modelDTO.getId()==null || modelDTO.getId()==0){
            staff=_mapper.toEntity(modelDTO);
        }
        //Update
        else {
            staff=getById(modelDTO.getId());
            _mapper.updateEntity(modelDTO,staff);
        }
        staff.setGender(Gender.valueOf(modelDTO.getGender()));
        staff.setProvinceCity(provinceCity);
        staff.setDepartment(department);
        staff.setPosition(position);
        _repo.save(staff);
    }

    @Override
    public void delete(List<Integer> ids) {
        for (int i = 0; i < ids.size(); i++) {
            _repo.deleteById(ids.get(i));
        }
    }

    // Write one StaffOutputSearchDTO to one Excel row
    private void writeRows(Sheet sheet, int rowIndex,
                           StaffOutputSearchDTO modelDTO)
    {
        Row row = sheet.createRow(rowIndex);

        row.createCell(0).setCellValue(modelDTO.getId());
        row.createCell(1).setCellValue(
                (modelDTO.getMiddleName()=="" || modelDTO.getMiddleName()==null)?
                        ( modelDTO.getLastName()+" "+ modelDTO.getFirstName()):
                        (modelDTO.getLastName()+" "+modelDTO.getMiddleName()+ " "+ modelDTO.getFirstName()));
        row.createCell(2).setCellValue(modelDTO.getAddress());
        row.createCell(3).setCellValue(modelDTO.getTel());
        row.createCell(4).setCellValue(modelDTO.getEmail());
        row.createCell(5).setCellValue(modelDTO.getSalary());
        row.createCell(6).setCellValue(modelDTO.getCitizenIDNumber());
        row.createCell(7).setCellValue(modelDTO.getGender()==Gender.Male ?"Male":"Female");
        row.createCell(8).setCellValue(modelDTO.getProvinceCityName());
        row.createCell(9).setCellValue(modelDTO.getDepartmentEnglishName());
        row.createCell(10).setCellValue(modelDTO.getPositionEnglishName());
        row.createCell(11).setCellValue(modelDTO.getHireDate()==null ?
                null:modelDTO.getHireDate().format(DATE_FORMAT));
        row.createCell(12).setCellValue(modelDTO.getEndDate()==null ?
                null:modelDTO.getEndDate().format(DATE_FORMAT));
        row.createCell(13).setCellValue(modelDTO.getBirthDate()==null ?
                null:modelDTO.getBirthDate().format(DATE_FORMAT));
    }

    // Build search specification
    private Specification<Staff> buildSpecification(
            StaffInputSearchDTO input) {

        return StaffSpecification.filter(input);
    }

    // It takes about 7 seconds to export 100000 items to an Excel file.
    @Override
    @Transactional(readOnly = true)
    public ByteArrayInputStream exportToExcel(StaffInputSearchDTO input)
//  "throws Exception" means "This method may throw a checked exception..."
//  For instance,
//  workStaff.write(output);
//  or
//  workStaff.close();
//  can throw IOException
            throws Exception
    {
//        To count time
//        long t1 = System.currentTimeMillis();

        Specification<Staff> specification = buildSpecification(input);
        SXSSFWorkbook workbook = new SXSSFWorkbook(500);
        Sheet sheet = workbook.createSheet("Staffs");
        String[] headerNames ={"Id","Name","Address", "Tel","Email","Salary","Citizen ID Number","Gender",
                "Province/City","Department","Position","HireDate","EndDate","BirthDate"};
        // Fixed column widths: These numbers are the widths of columns like
        //"Id","Name","Address", "Tel","Email",...
        // If I use .autoSizeColumn(...) instead of Fixed column widths,
        // it will be very slow.
        Integer[] headerWidths ={2500,6000,9000,4000,10000,2000,5000,2500,
                4000,7000,9000,3000,3000,3000};
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

            Specification<Staff> currentSpecification =
                    specification.and(
                            (root, query, builder) ->
                                    builder.greaterThan(root.get("id"), finalLastId));

            Page<Staff> page = _repo.findAll(currentSpecification, pageable);
            if (page.isEmpty()) {
                break;
            }
            List<StaffOutputSearchDTO> modelDTOs = _mapper.toOutputSearchDTOList(
                    page.getContent());

            for (StaffOutputSearchDTO dto : modelDTOs) {
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



