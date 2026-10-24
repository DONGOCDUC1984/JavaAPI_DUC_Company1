USE javaapi_duc_company1;
DROP PROCEDURE IF EXISTS PotteriesCreate;
DELIMITER $$
CREATE PROCEDURE PotteriesCreate(p_Name VARCHAR(255),p_Colour VARCHAR(100),p_IsMadeInVietnam INT,p_Price DECIMAL(10,2),
  p_PotteryCategoryId INT,p_ManufacturingDate DATE)
BEGIN
  INSERT INTO Potteries (name ,colour, is_made_in_vietnam, price, pottery_category_id, manufacturing_date ) 
  VALUES (p_Name,p_Colour,p_IsMadeInVietnam,p_Price,p_PotteryCategoryId,p_ManufacturingDate);

END$$

DELIMITER ;
