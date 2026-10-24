-- create database javaapi_duc_company1;
Use javaapi_duc_company1;

Insert into Districts (id, name, province_city_id)
VALUES 
 (1,'Ba Dinh' , 1 ),
 ( 2,'Cau Giay' , 1 ),
 ( 3, 'Hoan Kiem' , 1 ),
 ( 4,'Go Vap' , 2 ),
 ( 5,'Phu Nhuan' , 2 ),
 ( 6, 'Tan Binh' , 2 ),
 ( 7, 'Do Son' , 3 ),
 ( 8, 'Hong Bang' , 3 ),
 ( 9, 'Le Chan' , 3 ),
 ( 10, 'Ngo Quyen' , 3 );

Insert into Products (id, name,description,price, product_category_id,province_city_id, district_id ,image_url ,stock_quantity)
VALUES 
(1, 'Apple 1', 'Sweet.Made in Germany', 3, 1,1, 1, 'Apple1.jpg',1000),
(2, 'Apple 2', 'Good.Made in Sweden', 8, 1, 1,2, 'Apple2.jpg',1000),
(3, 'Apricot', 'Sweet.Made in Vietnam', 6, 1,1, 3, 'Apricot.jpg',1000),
(4, 'Banana', 'Sweet.Made in Vietnam', 2, 1, 2,4, 'Banana.jpg',1000),
(5, 'Bell Pepper', 'Made in Vietnam', 5, 1, 2,5, 'Bell Pepper.jpg',1000),
(6, 'Bread 1', 'Sweet.Made in Germany', 4, 2,2, 6, 'Bread1.jpg',1000),
(7, 'Broccoli', 'Made in Vietnam', 6, 1, 3,7, 'Broccoli.jpg',1000),
(8, 'Cabbage', 'High quality, made in Germany', 9, 1, 3, 8, 'Cabbage.jpg',1000),
(9, 'Carrot', 'Delicious, made in Britain', 2, 1, 3, 9, 'Carrot.jpg',1000),
(10, 'Cauliflower', 'High quality, made in Denmark', 11, 1,3, 10, 'Cauliflower.jpg',1000),
(11, 'Cherry', 'High quality.Made in Denmark', 12, 1, 1, 1, 'Cherry.jpg',1000),
(12, 'Cow Milk', 'With sugar.Made in Germany', 8, 3, 1, 2, 'Cow Milk.jpg',1000),
(13, 'Croissant', 'Made in Finland', 3, 2, 1, 3, 'Croissant.jpg',1000),
(14, 'Cucumber 1', 'Made in Germany', 2, 1, 2, 4, 'Cucumber1.jpg',1000),
(15, 'Cucumber 2', 'Made in Laos', 4, 1,2, 5, 'Cucumber2.jpg',1000),
(16, 'French loaf', 'Made in Vietnam', 5, 2,2, 6, 'French loaf.jpg',1000),
(17, 'Ginger', 'Made in Poland', 1, 1, 3, 7, 'Ginger.jpg',1000),
(18, 'Grapefruit', 'High quality, made in Sweden', 4, 1,3, 8, 'Grapefruit.jpg',1000),
(19, 'Grapes 1', 'Good.Made in Finland', 3, 1,3, 9, 'Grapes1.jpg',1000),
(20, 'Grapes 2', 'Good.Made in Norway', 7, 1,3, 10, 'Grapes2.jpg',1000),
(21, 'Soy Milk', 'Good.Made in Poland', 5, 3,1, 1, 'Soy Milk.jpg',1000),
(22, 'Tommaso', 'Good.Made in Norway', 2, 2,1, 2, 'Tommaso.jpg',1000);  

