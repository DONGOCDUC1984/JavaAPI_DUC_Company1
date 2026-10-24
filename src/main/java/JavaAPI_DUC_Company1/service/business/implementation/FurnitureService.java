package JavaAPI_DUC_Company1.service.business.implementation;

import JavaAPI_DUC_Company1.model.Furniture;
import JavaAPI_DUC_Company1.model.dto.furniture.FurnitureAddUpdateDTO;
import JavaAPI_DUC_Company1.model.dto.furniture.FurnitureInputSearchDTO;
import JavaAPI_DUC_Company1.model.dto.furniture.FurnitureOutputSearchDTO;
import JavaAPI_DUC_Company1.model.paginatedlist.PaginatedListModel;
import JavaAPI_DUC_Company1.repository.FurnitureRepository;
import JavaAPI_DUC_Company1.service.business.interfaces.IFurnitureService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import java.io.ByteArrayInputStream;
import java.sql.Date;
import java.util.List;
//import java.io.ByteArrayOutputStream;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.xssf.streaming.SXSSFWorkbook;
//import org.apache.poi.ss.usermodel.Row;
//import static JavaAPI_DUC_Company1.service.common.ExcelHelper.createHeaders;
//import static JavaAPI_DUC_Company1.service.common.FormatHelper.DATE_FORMAT;

@Service
@Transactional
//Spring JDBC Template for to search , filter with over 1,000,000 furnitures
//This Service utilizes both Spring JDBC Template and JPA
public class FurnitureService implements IFurnitureService {
    private final FurnitureRepository _repo;
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<FurnitureOutputSearchDTO> furnitureOutputSearchRowMapper =
            (rs, rowNum) -> {

                FurnitureOutputSearchDTO dto = new FurnitureOutputSearchDTO();
                dto.setId(rs.getInt("id"));
                dto.setName(rs.getString("name"));
                dto.setColour(rs.getString("colour"));
                dto.setIsMadeInVietnam(rs.getBoolean("isMadeInVietnam"));
                dto.setPrice(rs.getBigDecimal("price"));
                Date manufacturingDate = rs.getDate("manufacturingDate");

                if (manufacturingDate != null) {
                    dto.setManufacturingDate(manufacturingDate.toLocalDate());
                }

                dto.setFurnitureCategoryId(rs.getInt("furnitureCategoryId"));

                dto.setFurnitureCategoryName(
                        rs.getString("furnitureCategoryName"));

                return dto;
            };
    public FurnitureService(FurnitureRepository repo, JdbcTemplate jdbcTemplate)
    {
        _repo= repo;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveBatch(List<Furniture> potteries)
    {
        _repo.saveAll(potteries);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginatedListModel<FurnitureOutputSearchDTO> search
            (FurnitureInputSearchDTO input) {
        Integer isMadeInVietnam = null;

        if (input.getIsMadeInVietnam() != null) {
            isMadeInVietnam = input.getIsMadeInVietnam() ? 1 : 0;
        }

        List<FurnitureOutputSearchDTO> items =
                jdbcTemplate.query(
                        """
                        CALL FurnituresSearch(
                            ?, ?, ?, ?, ?, ?, ?, ?, ?, ?
                        )
                        """,
                        furnitureOutputSearchRowMapper,
                        input.getCurrentPage(),
                        input.getPageSize(),
                        input.getSearchName(),
                        input.getColour(),
                        isMadeInVietnam,
                        input.getMinPrice(),
                        input.getMaxPrice(),
                        input.getFurnitureCategoryId(),
                        input.getStartTime(),
                        input.getEndTime()
                );

        Long count = jdbcTemplate.queryForObject(
                """
                CALL FurnituresSearchCount(
                    ?, ?, ?, ?, ?, ?, ?, ?
                )
                """,
                Long.class,
                input.getSearchName(),
                input.getColour(),
                isMadeInVietnam,
                input.getMinPrice(),
                input.getMaxPrice(),
                input.getFurnitureCategoryId(),
                input.getStartTime(),
                input.getEndTime()
        );

        if (count == null) {
            count = 0L;
        }

        int totalPages =
                (int) Math.ceil(
                        (double) count / input.getPageSize());

        PaginatedListModel<FurnitureOutputSearchDTO> result =
                new PaginatedListModel<>();

        result.setItems(items);
        result.setCount(count);
        result.setCurrentPage(input.getCurrentPage());
        result.setPageSize(input.getPageSize());
        result.setTotalPages(totalPages);

        return result;
    }

    @Override
    public void addUpdate(FurnitureAddUpdateDTO modelDTO)
    {
        // CREATE
        if (modelDTO.getId() == null || modelDTO.getId() == 0) {
//      In Spring JDBC, the method name update() means:
//      Execute a SQL statement that changes data and return the number
//      of affected rows.Therefore,update() can be used for Insert, Update, Delete
            jdbcTemplate.update(
                    """
                    CALL FurnituresCreate(
                        ?, ?, ?, ?, ?, ?
                    )
                    """,
                    modelDTO.getName(),
                    modelDTO.getColour(),
                    Boolean.TRUE.equals(modelDTO.getIsMadeInVietnam()) ? 1 : 0,
                    modelDTO.getPrice(),
                    modelDTO.getFurnitureCategoryId(),
                    modelDTO.getManufacturingDate()
            );

        }
        // UPDATE
        else {
            Integer id = modelDTO.getId();

            Integer count = jdbcTemplate.queryForObject(
                    """
                    SELECT COUNT(*)
                    FROM Furnitures
                    WHERE id = ?
                    """,
                    Integer.class,
                    id
            );

            if (count == null || count == 0) {
                throw new RuntimeException(
                        "Furniture not found with id = " + id
                );
            }

            jdbcTemplate.update(
                    """
                    CALL FurnituresUpdate(
                        ?, ?, ?, ?, ?, ?, ?
                    )
                    """,
                    id,
                    modelDTO.getName(),
                    modelDTO.getColour(),
                    Boolean.TRUE.equals(modelDTO.getIsMadeInVietnam()) ? 1 : 0,
                    modelDTO.getPrice(),
                    modelDTO.getFurnitureCategoryId(),
                    modelDTO.getManufacturingDate()
            );
        }

    }

    //Delete method by Spring JDBC Template
//    @Override
//    public void delete(List<Integer> ids) {
//        for (Integer id : ids) {
//            Integer count = jdbcTemplate.queryForObject(
//                    """
//                    SELECT COUNT(*)
//                    FROM Furnitures
//                    WHERE id = ?
//                    """,
//                    Integer.class,
//                    id
//            );
//
//            if (count == null || count == 0) {
//                throw new RuntimeException(
//                        "Furniture not found with id = " + id
//                );
//            }
//
//            jdbcTemplate.update(
//                    """
//                    CALL FurnituresDelete(?)
//                    """,
//                    id
//            );
//        }
//    }

    // The above delete method by Spring JDBC Template can be replaced
    // by the following delete method by JPA
    //Delete method by JPA
    @Override
    public void delete(List<Integer> ids) {
        for (int i = 0; i < ids.size(); i++) {
            _repo.deleteById(ids.get(i));
        }
    }

}

