package JavaAPI_DUC_Company1.service.business.implementation;

import JavaAPI_DUC_Company1.mapper.FormerStaffMapper;
import JavaAPI_DUC_Company1.model.*;
import JavaAPI_DUC_Company1.model.dto.formerStaff.FormerStaffInputSearchDTO;
import JavaAPI_DUC_Company1.model.dto.formerStaff.FormerStaffOutputSearchDTO;
import JavaAPI_DUC_Company1.model.paginatedlist.PaginatedListModel;
import JavaAPI_DUC_Company1.repository.DepartmentRepository;
import JavaAPI_DUC_Company1.repository.PositionRepository;
import JavaAPI_DUC_Company1.repository.ProvinceCityRepository;
import JavaAPI_DUC_Company1.repository.FormerStaffRepository;
import JavaAPI_DUC_Company1.service.business.interfaces.IFormerStaffService;
import JavaAPI_DUC_Company1.specification.FormerStaffSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Service
@Transactional
public class FormerStaffService implements IFormerStaffService {
    private final FormerStaffRepository _repo;
    private final ProvinceCityRepository _provinceCityRepo;
    private final DepartmentRepository _departmentRepo;
    private final PositionRepository _positionRepo;
    private final FormerStaffMapper _mapper;

    public  FormerStaffService(FormerStaffRepository repo, ProvinceCityRepository provinceCityRepo,
                               DepartmentRepository departmentRepo, PositionRepository positionRepo,
                               FormerStaffMapper mapper){
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
    public PaginatedListModel<FormerStaffOutputSearchDTO> search(
            FormerStaffInputSearchDTO input) {
        //    Comparison
        //    C# EF Core	        Spring Boot
        //    IQueryable<FormerStaff>	    Specification<FormerStaff>
        //    .Where()	            Predicate
        //    .Include()	        @ManyToOne / fetch join if needed
        //    .CountAsync()	        page.getTotalElements()
        //    .Skip().Take()	    PageRequest
        //    .OrderBy()	        Sort.by("id")
        //    ToListAsync()	        Page<FormerStaff>
        Specification<FormerStaff> specification =
                FormerStaffSpecification.filter(input);

        Pageable pageable =
                PageRequest.of(
                        input.getCurrentPage() - 1,
                        input.getPageSize(),
                        Sort.by("id"));

        Page<FormerStaff> page = _repo.findAll(specification, pageable);

        PaginatedListModel<FormerStaffOutputSearchDTO> result = new PaginatedListModel<>();

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
    public FormerStaff getById(Integer id) {
        return _repo.findById(id).orElseThrow(() ->new RuntimeException("FormerStaff not found with id " + id) );
    }


}



