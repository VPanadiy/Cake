CREATE TABLE Book
(
  id SERIAL PRIMARY KEY,
  reader TEXT      NOT NULL,
  isbn TEXT      NOT NULL,
  title TEXT      NOT NULL,
  author TEXT      NOT NULL,
  description TEXT      NOT NULL
);

INSERT INTO Book (id, reader, isbn, title, author, description) VALUES (1, 'sd1', 'sdsqw', '13sdsd', 'sdsd25sda2', 'sdsdsd:');
INSERT INTO Book (id, reader, isbn, title, author, description) VALUES (2, 'sd2', 'sdws', 's1234dsd', 'sds54dsda2', 'sdsdsd:');
INSERT INTO Book (id, reader, isbn, title, author, description) VALUES (4,'sd3', 'sdws', 'sd45sd', 'sdsdsda2', 'sdsdsd:');

Select * from Book;

delete from book where id > 10;

CREATE TABLE Reader
(
  username TEXT PRIMARY KEY,
  fullname TEXT      NOT NULL,
  password TEXT      NOT NULL
);

INSERT INTO Reader (username, fullname, password) VALUES ('u1','user1', 'q');

Select * from Reader;

DROP TABLE role;

CREATE TABLE role (
  role_id SERIAL PRIMARY KEY,
  role varchar(255) DEFAULT NULL
);

INSERT INTO role (role) VALUES ('ADMIN');
INSERT INTO role (role) VALUES ('USER');

select * from role;

DROP TABLE user;

CREATE TABLE users (
  user_id SERIAL PRIMARY KEY,
  active int DEFAULT NULL,
  email varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  password varchar(255) NOT NULL
);

select * from users;

DROP TABLE user_role;

CREATE TABLE user_role (
  user_id int REFERENCES users (user_id),
  role_id int REFERENCES role (role_id)
);

select * from user_role;

DROP TABLE product;

CREATE TABLE IF NOT EXISTS Product (
  id SERIAL PRIMARY KEY,
  name VARCHAR(50),
  description  VARCHAR(255),
  price NUMERIC(9,2),
  productCategory  VARCHAR(50),
  weight DOUBLE PRECISION DEFAULT 0,
  count BIGINT DEFAULT 0,
  product_type VARCHAR(50),
  rating numeric(9,2) DEFAULT 0,
  image_data bytea
);

select * from product;

DROP TABLE Orders;

CREATE TABLE Orders
(
  order_id SERIAL PRIMARY KEY,
  order_date DATE,
  user_id INT,
  price REAL,
  is_open BOOLEAN DEFAULT TRUE
);

select * from Orders;

DROP TABLE order_details;

CREATE TABLE order_details
(
  id SERIAL,
  order_id BIGINT REFERENCES Orders (order_id),
  product_id BIGINT,
  count INT,
  payment BOOLEAN NOT NULL DEFAULT FALSE
);

select * from order_details;

DROP TABLE user_comments;

CREATE TABLE user_comments
(
  id bigint NOT NULL,
  user_id integer REFERENCES users (user_id),
  product_id bigint REFERENCES product (id),
  post text NOT NULL,
  user_ip VARCHAR(15),
  local_date_time TIMESTAMP
);

select * from user_comments;

CREATE TABLE schedule
(
  id SERIAL PRIMARY KEY,
  title varchar(255) NOT NULL,
  description TEXT      ,
  name varchar(255) NOT NULL,
  phone varchar(13),
  date_order timestamp NOT NULL,
  active int DEFAULT NULL
);

DROP TABLE schedule;

INSERT INTO schedule (id, title, description, name, phone, date_order, active) VALUES (1, 'title1', 'description1', 'name1', '+380631112233', '2017-11-11 00:00:00', 0);
INSERT INTO schedule (id, title, description, name, phone, date_order, active) VALUES (2, 'title2', 'description2', 'name2', '+380631112233', '2017-11-11 00:00:00', 0);
INSERT INTO schedule (id, title, description, name, phone, date_order, active) VALUES (3, 'title3', 'description3', 'name3', '+380631112233', '2017-11-11 00:00:00', 1);
INSERT INTO schedule (id, title, description, name, phone, date_order, active) VALUES (4, 'title4', 'description3', 'name3', '+380631112233', '2017-10-11 00:00:00', 1);
INSERT INTO schedule (id, title, description, name, phone, date_order, active) VALUES (5, 'title5', 'description1', 'name1', '+380631112233', '2017-11-01 00:00:00', 0);
INSERT INTO schedule (id, title, description, name, phone, date_order, active) VALUES (6, 'title6', 'description2', 'name2', '+380631112233', '2017-11-15 00:00:00', 0);
INSERT INTO schedule (id, title, description, name, phone, date_order, active) VALUES (7, 'title7', 'description1', 'name1', '+380631112233', '2017-08-17 00:00:00', 0);
INSERT INTO schedule (id, title, description, name, phone, date_order, active) VALUES (8, 'title8', 'description2', 'name2', '+380631112233', '2017-11-22 00:00:00', 0);

select * from schedule;