﻿CREATE TABLE Book
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