-- 各種テーブル削除
DROP TABLE IF EXISTS student CASCADE;
DROP TABLE IF EXISTS staff cascade;
DROP TABLE IF EXISTS admin CASCADE;
DROP TABLE IF EXISTS sale_list CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS bookinfo CASCADE;
DROP TABLE IF EXISTS bookmark cascade;
DROP TABLE IF EXISTS bought_history cascade;
DROP TABLE IF EXISTS bought_certificate cascade;
DROP TABLE IF EXISTS request cascade;

-- 学生アカウントテーブル
CREATE TABLE student(
id SERIAL,
student_name varchar(255) NOT NULL,
birthday date NOT NULL,
address varchar(255) NOT NULL,
student_email varchar(255) NOT NULL UNIQUE,
student_pass varchar(255) NOT NULL,
student_number varchar(255) NOT NULL UNIQUE,
bank_account varchar(255),
student_status INTEGER NOT NULL,
ban_day TimeStamp,

PRIMARY KEY (id)
);

-- 教員アカウントテーブル
 CREATE TABLE staff
(
id SERIAL,
staff_name VARCHAR(255) NOT NULL,
staff_email VARCHAR(255) NOT NULL UNIQUE,
staff_pass VARCHAR(255) NOT NULL,
staff_number VARCHAR(255) NOT NULL UNIQUE,

PRIMARY KEY (id)
);

-- 管理者アカウントテーブル
CREATE TABLE admin(
id SERIAL,
admin_name TEXT,
admin_pass TEXT,

PRIMARY KEY (id)
);

-- カテゴリーテーブル
CREATE TABLE categories(
id SERIAL,
category_name VARCHAR(255),

PRIMARY KEY(id)
);

-- 書籍情報テーブル
CREATE TABLE bookinfo(
id SERIAL,
category_id INTEGER NOT NULL,
title VARCHAR(255) NOT NULL,
author VARCHAR(255) NOT NULL,
publisher VARCHAR(255) NOT NULL,
isbn VARCHAR(255) NOT NULL UNIQUE,
grade INTEGER NOT NULL,
class VARCHAR(255) NOT NULL,
condition VARCHAR(255) NOT NULL,
price INTEGER NOT NULL,

PRIMARY KEY(id),
FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- 出品リストテーブル
CREATE TABLE sale_list(
id SERIAL,
student_id INTEGER NOT NULL,
bookinfo_id INTEGER NOT NULL,
sale_day DATE NOT NULL,
item_status INTEGER NOT NULL,
sale_method INTEGER NOT NULL,

PRIMARY KEY(id),
FOREIGN KEY(student_id) REFERENCES student(id),
FOREIGN KEY(bookinfo_id) REFERENCES bookinfo(id)
);



-- 商品ブックマークテーブル
CREATE TABLE bookmark
 	(
id SERIAL,
student_id INTEGER NOT NULL,
salelist_id INTEGER NOT NULL,

PRIMARY KEY (id),
FOREIGN KEY(student_id) references student(id), 
FOREIGN KEY(salelist_id) references sale_list(id) 
);


-- 購入履歴
CREATE TABLE bought_history
(
id SERIAL,
student_id INTEGER NOT NULL , 
salelist_id INTEGER NOT NULL,
payment INTEGER NOT NULL,
accept INTEGER NOT NULL,
delivery INTEGER,

PRIMARY KEY (id),
FOREIGN KEY(student_id) references student(id), 
FOREIGN KEY(salelist_id) references sale_list(id) 
);

--購入証明書
CREATE TABLE bought_certificate
(
id SERIAL,
salelist_id INTEGER NOT NULL,
bought_day TimeStamp NOT NULL,

PRIMARY KEY (id),
FOREIGN KEY(salelist_id) references sale_list(id)
);

--入荷予約
CREATE TABLE request
(
id SERIAL,
student_id INTEGER NOT NULL, 
bookinfo_id INTEGER NOT NULL, 

PRIMARY KEY (id),
FOREIGN KEY(student_id) references student(id),
FOREIGN KEY(bookinfo_id) references bookinfo(id)
);
