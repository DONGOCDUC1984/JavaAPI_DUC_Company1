USE javaapi_duc_company1;
DROP PROCEDURE IF EXISTS PotteriesExport;
DELIMITER $$
CREATE PROCEDURE PotteriesExport(IN p_lastId INT,IN p_batchSize INT,IN p_searchName VARCHAR(255),IN p_colour VARCHAR(100),
    IN p_isMadeInVietnam INT,IN p_minPrice DECIMAL(10,2),IN p_maxPrice DECIMAL(10,2),IN p_potteryCategoryId INT,
    IN p_startTime DATE,IN p_endTime DATE
)
BEGIN

    SELECT
        p.id AS id,
        p.name AS name,
        p.colour AS colour,
        p.is_made_in_vietnam AS isMadeInVietnam,
        p.price AS price,
        p.manufacturing_date AS manufacturingDate,

        c.id AS potteryCategoryId,
        c.name AS potteryCategoryName
    FROM potteries p
    JOIN pottery_categories c
        ON p.pottery_category_id = c.id
    WHERE
        p.id > p_lastId

        AND (
            p_searchName IS NULL
            OR p_searchName = ''
            OR p.name LIKE CONCAT('%', p_searchName, '%')
        )

        AND (
            p_colour IS NULL
            OR p_colour = ''
            OR p.colour = p_colour
        )

        AND (
            p_isMadeInVietnam IS NULL
            OR p.is_made_in_vietnam = p_isMadeInVietnam
        )

        AND (
            p_minPrice IS NULL
            OR p.price >= p_minPrice
        )

        AND (
            p_maxPrice IS NULL
            OR p.price <= p_maxPrice
        )

        AND (
            p_potteryCategoryId IS NULL
            OR p_potteryCategoryId =0
            OR p.pottery_category_id = p_potteryCategoryId
        )

        AND (
            p_startTime IS NULL
            OR p.manufacturing_date >= p_startTime
        )

        AND (
            p_endTime IS NULL
            OR p.manufacturing_date <= p_endTime
        )

    ORDER BY p.id ASC
    LIMIT p_batchSize;
END$$
DELIMITER ;