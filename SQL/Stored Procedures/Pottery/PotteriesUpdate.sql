USE javaapi_duc_company1;
DROP PROCEDURE IF EXISTS PotteriesUpdate;
DELIMITER $$
CREATE PROCEDURE PotteriesUpdate(p_Id INT,p_Name VARCHAR(255),p_Colour VARCHAR(100),p_IsMadeInVietnam INT
  ,p_Price DECIMAL(10,2),  p_PotteryCategoryId INT,p_ManufacturingDate DATE)
BEGIN
  Update Potteries
    Set 
     name=p_Name,
     colour=p_Colour, 
     is_made_in_vietnam=p_IsMadeInVietnam, 
     price=p_Price, 
     pottery_category_id=p_PotteryCategoryId, 
     manufacturing_date =p_ManufacturingDate
    Where  
    id=p_Id ;

END$$

DELIMITER ;
