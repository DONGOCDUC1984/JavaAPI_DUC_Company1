USE javaapi_duc_company1;
DROP PROCEDURE IF EXISTS PotteriesDelete;
DELIMITER $$
CREATE PROCEDURE PotteriesDelete(p_Id INT)
BEGIN
  Delete from Potteries
  Where Id=p_Id ;
END$$

DELIMITER ;
