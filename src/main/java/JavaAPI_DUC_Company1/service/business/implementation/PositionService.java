package JavaAPI_DUC_Company1.service.business.implementation;

import JavaAPI_DUC_Company1.mapper.PositionMapper;
import JavaAPI_DUC_Company1.model.Department;
import JavaAPI_DUC_Company1.model.Position;
import JavaAPI_DUC_Company1.model.PositionCategory;
import JavaAPI_DUC_Company1.model.dto.position.PositionAddUpdateDTO;
import JavaAPI_DUC_Company1.model.dto.position.PositionOutputSearchDTO;
import JavaAPI_DUC_Company1.repository.DepartmentRepository;
import JavaAPI_DUC_Company1.repository.PositionCategoryRepository;
import JavaAPI_DUC_Company1.repository.PositionRepository;
import JavaAPI_DUC_Company1.service.business.interfaces.IPositionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class PositionService implements IPositionService {
    private final PositionRepository _repo;
    private final DepartmentRepository _departmentrepo;
    private final PositionCategoryRepository _positionCategoryrepo;
    private final PositionMapper _mapper;
    public  PositionService(PositionRepository repo, DepartmentRepository departmentrepo,
                            PositionCategoryRepository positionCategoryrepo, PositionMapper mapper){
        _repo=repo;
        _departmentrepo = departmentrepo;
        _positionCategoryrepo = positionCategoryrepo;
        _mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PositionOutputSearchDTO> getAll() {
        List<PositionOutputSearchDTO> listDTO=_mapper.toOutputSearchDTOList(_repo.findAll()) ;
        return listDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Position getById(Integer id) {
        return _repo.findById(id)
                .orElseThrow(() ->new RuntimeException("Position not found with id " + id) );
    }

    @Override
    public Position addUpdate(PositionAddUpdateDTO modelDTO) {
        PositionCategory positionCategory=_positionCategoryrepo
                .findById(modelDTO.getPositionCategoryId())
                .orElseThrow(() ->new RuntimeException("PositionCategory not found") );
        Department department=_departmentrepo
                .findById(modelDTO.getDepartmentId())
                .orElseThrow(() ->new RuntimeException("Department not found") );
        Position position;
        //Add
        if (modelDTO.getId()==null || modelDTO.getId()==0){
            position=_mapper.toEntity(modelDTO);
        }
        //Update
        else {
            position=getById(modelDTO.getId());
            _mapper.updateEntity(modelDTO,position);
        }
        position.setDepartment(department);
        position.setPositionCategory(positionCategory);
        return _repo.save(position);
    }

    @Override
    public void delete(Integer id)
    {
        _repo.deleteById(id);
    }

}

