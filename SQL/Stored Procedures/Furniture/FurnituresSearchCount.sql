use javaapi_duc_company1;
DROP PROCEDURE IF EXISTS FurnituresSearchCount;
DELIMITER $$
CREATE PROCEDURE FurnituresSearchCount(
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
    SELECT COUNT(*) AS totalCount
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
        );

END$$

DELIMITER ;