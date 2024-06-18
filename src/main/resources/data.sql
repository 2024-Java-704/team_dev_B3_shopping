INSERT INTO images(name, file_path)
VALUES('search.png', 'src/main/resources/static/img\search.png');
INSERT INTO images(name, file_path)
VALUES('search.png', 'src/main/resources/static/img\search.png');
INSERT INTO images(name, file_path)
VALUES('work_shigoto_osame_man.png', 'src/main/resources/static/img\work_shigoto_osame_man.png.png');
INSERT INTO images(name, file_path)
VALUES('animal_wani_water.png', 'src/main/resources/static/img\animal_wani_water.png');
INSERT INTO images(name, file_path)
VALUES('work_shigoto_osame_man.png', 'src/main/resources/static/img\work_shigoto_osame_man.png');
INSERT INTO images(name, file_path)
VALUES('book_japan_manyousyuu.png', 'src/main/resources/static/img\book_japan_manyousyuu.png');
INSERT INTO images(name, file_path)
VALUES('heart_inuku_woman.png', 'src/main/resources/static/img\heart_inuku_woman.png');
INSERT INTO images(name, file_path)
VALUES('book_law_roppouzensyo.png', 'src/main/resources/static/img\book_law_roppouzensyo.png');
INSERT INTO images(name, file_path)
VALUES('money_weak_yen_strong_dollar.png', 'src/main/resources/static/img\money_weak_yen_strong_dollar.png');
INSERT INTO images(name, file_path)
VALUES('work_shigoto_osame_man.png', 'src/main/resources/static/img\work_shigoto_osame_man.png');
INSERT INTO images(name, file_path)
VALUES('job_science_teacher.png', 'src/main/resources/static/img\job_science_teacher.png');
INSERT INTO images(name, file_path)
VALUES('nigaoe_tokugawa_tsunayoshi.png', 'src/main/resources/static/img\nigaoe_tokugawa_tsunayoshi.png');


INSERT INTO students(student_name,birthday, address, student_email, student_pass, student_number, bank_account, student_status, image_id) 
VALUES('佐藤','2000-01-01','東京','a@a','himitu','a000001','1234',1,1);
INSERT INTO students(student_name,birthday, address, student_email, student_pass, student_number, bank_account, student_status, ban_day, image_id) 
VALUES('鈴木','2000-02-02','神奈川','b@b','himitu','b000001','1234',3,CURRENT_TIMESTAMP,2);
INSERT INTO students(student_name,birthday, address, student_email, student_pass, student_number, bank_account, student_status, image_id) 
VALUES('田中','2000-03-03','埼玉','c@c','himitu','c000001','1234',2,3);
INSERT INTO students(student_name,birthday, address, student_email, student_pass, student_number, bank_account, student_status, image_id) 
VALUES('倉沢','2000-04-04','千葉','d@d','himitu','d000001','1234',4,4);
INSERT INTO students(student_name,birthday, address,student_email,student_pass,student_number,bank_account,student_status, image_id) 
VALUES('獄裏番之助','2000-01-02','東京','tuppari@gmail.com','himitu','s14550','4649',5,5);


INSERT INTO staff(staff_name,staff_email,staff_pass,staff_number) VALUES('大澤','a@a','himitu','a002001');
INSERT INTO staff(staff_name,staff_email,staff_pass,staff_number) VALUES('中山','b@b','himitu','b002002');


INSERT INTO admin(admin_name,admin_pass) VALUES('admin','himitu');


INSERT INTO categories(category_name) VALUES('文学部');
INSERT INTO categories(category_name) VALUES('教育学部');
INSERT INTO categories(category_name) VALUES('法学部');
INSERT INTO categories(category_name) VALUES('社会学部');
INSERT INTO categories(category_name) VALUES('経済学部');
INSERT INTO categories(category_name) VALUES('理学部');
INSERT INTO categories(category_name) VALUES('医学部');
INSERT INTO categories(category_name) VALUES('歯学部');
INSERT INTO categories(category_name) VALUES('薬学部');
INSERT INTO categories(category_name) VALUES('工学部');
INSERT INTO categories(category_name) VALUES('農学部');
INSERT INTO categories(category_name) VALUES('その他');


INSERT INTO bookinfo(category_id,title,author,publisher,isbn,grade,lecture,condition,price, image_id) VALUES('1','春はあけぼの','清少納言','岩波文庫','9784-0226-30575',1,'文学部概論','よきかな',1200, 6);
INSERT INTO bookinfo(category_id,title,author,publisher,isbn,grade,lecture,condition,price, image_id) VALUES('2','心のノート','文部科学省','文部科学省','0987-6543-2109',2,'道徳','いとをかし',1300, 7);
INSERT INTO bookinfo(category_id,title,author,publisher,isbn,grade,lecture,condition,price, image_id) VALUES('3','ポケット六法','佐伯','有斐閣','9784-64100-9240',3,'法学概論','あし',1400, 8);
INSERT INTO bookinfo(category_id,title,author,publisher,isbn,grade,lecture,condition,price, image_id) VALUES('4','やさしい経済教室','加藤','小学館','1234-5678-91011',4,'経済学入門','いとあし',1500, 9);
INSERT INTO bookinfo(category_id,title,author,publisher,isbn,grade,lecture,condition,price, image_id) VALUES('5','青チャート','福山','数研出版','0141-0584-1570',1,'高校数学','よきかな',1600, 10);
INSERT INTO bookinfo(category_id,title,author,publisher,isbn,grade,lecture,condition,price, image_id) VALUES('6','理学部とは','ガリレオ','ガリレオ出版','0123-4567-8910',1,'理学部へようこそ','あし',1800, 11);
INSERT INTO bookinfo(category_id,title,author,publisher,isbn,grade,lecture,condition,price, image_id) VALUES('6','甘えん坊将軍','徳川偉人','徳川出版','0123-4567-9845',1,'徳川学入門','いとわろし',2900, 12);


INSERT INTO sale_list(student_id,bookinfo_id,sale_day,item_status,sale_method) VALUES('1','1','2024-06-13',1,1);
INSERT INTO sale_list(student_id,bookinfo_id,sale_day,item_status,sale_method) VALUES('3','2','2024-06-12',2,2);
INSERT INTO sale_list(student_id,bookinfo_id,sale_day,item_status,sale_method) VALUES('3','3','2024-06-11',3,1);
INSERT INTO sale_list(student_id,bookinfo_id,sale_day,item_status,sale_method) VALUES('4','4','2024-06-10',4,2);
INSERT INTO sale_list(student_id,bookinfo_id,sale_day,item_status,sale_method) VALUES('5','5','2024-06-09',1,1);
INSERT INTO sale_list(student_id,bookinfo_id,sale_day,item_status,sale_method) VALUES('5','6','2024-06-08',5,1);
INSERT INTO sale_list(student_id,bookinfo_id,sale_day,item_status,sale_method) VALUES('3','7','2024-06-08',2,1);


INSERT INTO bookmark(student_id,salelist_id) VALUES(1,1);
INSERT INTO bookmark(student_id,salelist_id) VALUES(1,3);
INSERT INTO bookmark(student_id,salelist_id) VALUES(2,2);
INSERT INTO bookmark(student_id,salelist_id) VALUES(2,4);
INSERT INTO bookmark(student_id,salelist_id) VALUES(3,1);
INSERT INTO bookmark(student_id,salelist_id) VALUES(3,3);
INSERT INTO bookmark(student_id,salelist_id) VALUES(3,5);
INSERT INTO bookmark(student_id,salelist_id) VALUES(4,4);
INSERT INTO bookmark(student_id,salelist_id) VALUES(4,5);
INSERT INTO bookmark(student_id,salelist_id) VALUES(5,5);
INSERT INTO bookmark(student_id,salelist_id) VALUES(5,1);


INSERT INTO bought_history(student_id,salelist_id,payment,accept,delivery) VALUES(1,2,1,1,1);
INSERT INTO bought_history(student_id,salelist_id,payment,accept,delivery) VALUES(3,7,2,2,2);


INSERT INTO bought_certificate(salelist_id,bought_day) VALUES(2,CURRENT_TIMESTAMP);
INSERT INTO bought_certificate(salelist_id,bought_day) VALUES(3,CURRENT_TIMESTAMP);
INSERT INTO bought_certificate(salelist_id,bought_day) VALUES(4,CURRENT_TIMESTAMP);


INSERT INTO hope(student_id,bookinfo_id,status) VALUES(1,5,1);
INSERT INTO hope(student_id,bookinfo_id,status) VALUES(2,6,1);