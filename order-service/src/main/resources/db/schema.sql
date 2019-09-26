CREATE TABLE statuses (
  id serial primary key,
  name varchar(255),
  description varchar(255)
);
CREATE TABLE orders (
  id serial primary key,
  car_id int NOT NULL,
  last_name varchar(255) NOT NULL,
  first_name varchar(255) NOT NULL,
  middle_name varchar(255),
  client_phone_number varchar(255),
  client_email varchar(255),
  start_rent_date timestamp,
  end_rent_date timestamp,
  location_start varchar(255),
  location_end varchar(255),
  state int,
  manager varchar(255),
  create_date timestamp,
  comment varchar(255),
  FOREIGN KEY (state) REFERENCES statuses(id)
);
CREATE SEQUENCE order_sequence  INCREMENT 1  MINVALUE 1;


