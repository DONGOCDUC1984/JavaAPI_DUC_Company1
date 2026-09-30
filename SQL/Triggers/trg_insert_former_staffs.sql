use javaapi_duc_company1;
 -- This trigger is in table staffs
DROP TRIGGER IF EXISTS trg_insert_former_staffs;
DELIMITER //
CREATE TRIGGER trg_insert_former_staffs
   Before Delete on staffs 
   For each row 
   Begin 
      Insert into former_staffs ( old_id, first_name, middle_name, last_name, address, tel, email, salary, citizenidnumber, 
             gender, province_city_id,department_id, position_id, hire_date, end_date, birth_date)
      Values (old.id, old.first_name, old.middle_name, old.last_name, old.address, old.tel, old.email, old.salary, old.citizenidnumber, 
             old.gender, old.province_city_id,old.department_id, old.position_id, old.hire_date,old.end_date,old.birth_date)
	  ;
   END;
//

DELIMITER ;

