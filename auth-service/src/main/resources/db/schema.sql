CREATE TABLE role (
  id  serial primary key ,
  description varchar(255) DEFAULT NULL,
  role_name varchar(255) DEFAULT NULL
);
CREATE TABLE users (
  id serial primary key,
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  middle_name varchar(255),
  password varchar(255) NOT NULL,
  username varchar(255) NOT NULL,
  birthday timestamp(6),
  appointment_date timestamp(6),
  location varchar(255),
  role_id int,
  mobile varchar(255),
  email varchar(255),
  passport varchar(255),
  FOREIGN KEY (role_id) REFERENCES role(id)
);
CREATE SEQUENCE user_sequence START WITH 5;

