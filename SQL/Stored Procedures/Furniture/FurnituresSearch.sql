use javaapi_duc_company1;
DROP PROCEDURE IF EXISTS FurnituresSearch;

DELIMITER $$

CREATE PROCEDURE FurnituresSearch(
    IN p_currentPage INT,
    IN p_pageSize INT,
    IN p_searchName VARCHAR(255),
    IN p_colour VARCHAR(100),
    IN p_isMadeInVietnam INT,
    IN p_minPrice DECIMAL(10,2),
    IN p_maxPrice DECIMAL(10,2),
    IN p_furnitureCategoryId INT,
    IN p_startTime DATE,
    IN p_endTime DATE
)
BEGIN

    DECLARE v_offset INT DEFAULT 0;
    DECLARE v_startId INT DEFAULT 0;

    SET v_offset = (p_currentPage - 1) * p_pageSize;

    /*
       Find the first ID of the requested page.
       This still uses OFFSET to locate the page,
       but the final query can then start from that ID.
    */
    SELECT f.Id
    INTO v_startId
    FROM Furnitures f
    WHERE
        (p_searchName IS NULL
            OR p_searchName = ''
            OR f.name LIKE CONCAT('%', p_searchName, '%'))

        AND (p_colour IS NULL
            OR p_colour = ''
            OR f.colour = p_colour)

        AND (
            p_isMadeInVietnam IS NULL
            OR f.is_made_in_vietnam = p_isMadeInVietnam
        )

        AND (
            p_minPrice IS NULL
            OR f.price >= p_minPrice
        )

        AND (
            p_maxPrice IS NULL
            OR f.price <= p_maxPrice
        )

        AND (
            p_furnitureCategoryId IS NULL
            OR p_furnitureCategoryId =0
            OR f.furniture_category_id = p_furnitureCategoryId
        )

        AND (
            p_startTime IS NULL
            OR f.manufacturing_date >= p_startTime
        )

        AND (
            p_endTime IS NULL
            OR f.manufacturing_date <= p_endTime
        )

    ORDER BY f.Id ASC
    LIMIT 1 OFFSET v_offset;


    /*
       Return the actual page.
    */
    SELECT
        f.id AS id,
        f.name AS name,
        f.colour AS colour,
        f.is_made_in_vietnam AS isMadeInVietnam,
        f.price AS price,
        f.manufacturing_date  AS manufacturingDate,

        c.id AS furnitureCategoryId,
        c.name AS furnitureCategoryName

    FROM Furnitures f

    JOIN furniture_categories c
        ON f.furniture_category_id = c.Id

    WHERE
        (p_searchName IS NULL
            OR p_searchName = ''
            OR f.name LIKE CONCAT('%', p_searchName, '%'))

        AND (p_colour IS NULL
            OR p_colour = ''
            OR f.colour = p_colour)

        AND (
            p_isMadeInVietnam IS NULL
            OR f.is_made_in_vietnam = p_isMadeInVietnam
        )

        AND (
            p_minPrice IS NULL
            OR f.price >= p_minPrice
        )

        AND (
            p_maxPrice IS NULL
            OR f.price <= p_maxPrice
        )

        AND (
            p_furnitureCategoryId IS NULL
            OR p_furnitureCategoryId =0
            OR f.furniture_category_id = p_furnitureCategoryId
        )

        AND (
            p_startTime IS NULL
            OR f.manufacturing_date >= p_startTime
        )

        AND (
            p_endTime IS NULL
            OR f.manufacturing_date <= p_endTime
        )

        AND f.Id >= v_startId

    ORDER BY f.Id ASC

    LIMIT p_pageSize;

END$$

DELIMITER ;