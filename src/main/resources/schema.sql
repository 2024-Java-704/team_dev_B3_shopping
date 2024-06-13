-- 各種テーブル削除
DROP TABLE IF EXISTS bought_certificate CASCADE;
DROP TABLE IF EXISTS bought_history CASCADE;
DROP TABLE IF EXISTS bookmark CASCADE;
DROP TABLE IF EXISTS sale_list CASCADE;
DROP TABLE IF EXISTS request CASCADE;
DROP TABLE IF EXISTS bookinfo CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS admin CASCADE;
DROP TABLE IF EXISTS staff CASCADE;
DROP TABLE IF EXISTS students CASCADE;

-- 学生アカウント
CREATE TABLE students(
id SERIAL,
student_name VARCHAR(255) NOT NULL,
birthday DATE NOT NULL,
address VARCHAR(255) NOT NULL,
student_email VARCHAR(255) NOT NULL UNIQUE,
student_pass VARCHAR(255) NOT NULL,
student_number VARCHAR(255) NOT NULL UNIQUE,
bank_account VARCHAR(255),
student_status INTEGER NOT NULL, -- 1:仮登録 2:本登録 3:凍結中 4:退会済 5:申請却下
ban_day TIMESTAMP,

PRIMARY KEY (id)
);


-- 教員アカウント
 CREATE TABLE staff
(
id SERIAL,
staff_name VARCHAR(255) NOT NULL,
staff_email VARCHAR(255) NOT NULL UNIQUE,
staff_pass VARCHAR(255) NOT NULL,
staff_number VARCHAR(255) NOT NULL UNIQUE,

PRIMARY KEY (id)
);

-- 管理者アカウント
CREATE TABLE admin(
id SERIAL,
admin_name VARCHAR(255),
admin_pass VARCHAR(255),

PRIMARY KEY (id)
);

-- カテゴリー
CREATE TABLE categories(
id SERIAL,
category_name VARCHAR(255),

PRIMARY KEY(id)
);

-- 書籍情報
CREATE TABLE bookinfo(
id SERIAL,
category_id INTEGER NOT NULL,
title VARCHAR(255) NOT NULL,
author VARCHAR(255) NOT NULL,
publisher VARCHAR(255) NOT NULL,
isbn VARCHAR(255) NOT NULL UNIQUE,
grade INTEGER NOT NULL,
lecture VARCHAR(255) NOT NULL,
condition VARCHAR(255) NOT NULL,
price INTEGER,

PRIMARY KEY(id),
FOREIGN KEY (category_id) REFERENCES categories(id)
);

--入荷予約
CREATE TABLE request
(
id SERIAL,
student_id INTEGER NOT NULL , 
bookinfo_id INTEGER NOT NULL , 

PRIMARY KEY (id),
FOREIGN KEY(student_id) REFERENCES students(id),
FOREIGN KEY(bookinfo_id) REFERENCES bookinfo(id)
);

-- 出品リスト
CREATE TABLE sale_list(
id SERIAL,
student_id INTEGER NOT NULL,
bookinfo_id INTEGER NOT NULL,
sale_day DATE NOT NULL,
item_status INTEGER NOT NULL, -- 1:出品中 2:売買済み 3:売上受取申請済 4:売上受取済 5:出品申請中 6:出費申請却下
sale_method INTEGER NOT NULL,

PRIMARY KEY(id),
FOREIGN KEY(student_id) REFERENCES students(id),
FOREIGN KEY(bookinfo_id) REFERENCES bookinfo(id)
);

-- 商品ブックマークテーブル
CREATE TABLE bookmark
 (
id SERIAL,
student_id INTEGER NOT NULL,
salelist_id INTEGER NOT NULL,

PRIMARY KEY (id),
FOREIGN KEY(student_id) REFERENCES students(id), 
FOREIGN KEY(salelist_id) REFERENCES sale_list(id) 
);

-- 購入履歴
CREATE TABLE bought_history
(
id SERIAL,
student_id INTEGER NOT NULL ,
salelist_id INTEGER NOT NULL,
payment INTEGER NOT NULL,
accept INTEGER NOT NULL, -- 1:郵送 2:窓口受取
delivery INTEGER, -- 1:受渡準備完了 2:受渡完了 3:発送完了

PRIMARY KEY (id),
FOREIGN KEY(student_id) REFERENCES students(id), 
FOREIGN KEY(salelist_id) REFERENCES sale_list(id) 
);

--購入証明書
CREATE TABLE bought_certificate
(
id SERIAL,
salelist_id INTEGER NOT NULL,
bought_day TIMESTAMP NOT NULL,

PRIMARY KEY (id),
FOREIGN KEY(salelist_id) REFERENCES sale_list(id)
);