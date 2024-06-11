INSERT INTO students(student_name,birthday, address, student_email, student_pass, student_number, bank_account, student_status) 
VALUES('aaa','2000-01-01','東京','a@a','himitu','a0001','1234',1);
INSERT INTO students(student_name,birthday, address, student_email, student_pass, student_number, bank_account, student_status, ban_day) 
VALUES('bbb','2000-02-02','東京','b@b','himitu','b0001','1234',3,CURRENT_TIMESTAMP);
INSERT INTO students(student_name,birthday, address, student_email, student_pass, student_number, bank_account, student_status) 
VALUES('ccc','2000-03-03','東京','c@c','himitu','c0001','1234',2);

INSERT INTO staff(staff_name,staff_email,staff_pass,staff_number) VALUES('aaa','b@b','himitu','b0001');

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

INSERT INTO bookinfo(category_id,title,author,publisher,isbn,grade,lecture,condition,price) VALUES('1','aaa','a','c','1234-5678-9012',1,'A','a',1200);
INSERT INTO bookinfo(category_id,title,author,publisher,isbn,grade,lecture,condition,price) VALUES('2','bbb','b','c','0987-6543-2109',2,'B','b',1300);

INSERT INTO sale_list(student_id,bookinfo_id,sale_day,item_status,sale_method) VALUES('1','1','2000-01-01',1,1);
INSERT INTO sale_list(student_id,bookinfo_id,sale_day,item_status,sale_method) VALUES('1','2','2001-02-02',2,2);

INSERT INTO bookmark(student_id,salelist_id) VALUES(1,1);
INSERT INTO bookmark(student_id,salelist_id) VALUES(2,2);

INSERT INTO bought_history(student_id,salelist_id,payment,accept,delivery) VALUES(1,1,1,1,1);
INSERT INTO bought_history(student_id,salelist_id,payment,accept,delivery) VALUES(2,2,2,2,2);

INSERT INTO bought_certificate(salelist_id,bought_day) VALUES(1,CURRENT_TIMESTAMP);
INSERT INTO bought_certificate(salelist_id,bought_day) VALUES(2,CURRENT_TIMESTAMP);

INSERT INTO request(student_id,bookinfo_id) VALUES(1,1);
INSERT INTO request(student_id,bookinfo_id) VALUES(2,2);