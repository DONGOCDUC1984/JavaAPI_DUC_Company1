USE javaapi_duc_company1;
DROP PROCEDURE IF EXISTS FurnituresDelete;
DELIMITER $$
CREATE PROCEDURE FurnituresDelete(p_Id INT)
BEGIN
  Delete from furnitures
  Where Id=p_Id ;
END$$

DELIMITER ;
