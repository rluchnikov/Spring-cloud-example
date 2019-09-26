CREATE SEQUENCE hibernate_sequence  INCREMENT 1  MINVALUE 1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
CREATE SEQUENCE car_sequence START WITH 101;
CREATE TABLE locations (
  id serial primary key,
  name varchar(255),
  address varchar(255),
  type varchar(255)
);
CREATE TABLE statuses (
  id serial primary key,
  name varchar(255),
  description varchar(255)
);
CREATE TABLE cars
(
  id serial primary key,
  registration_number varchar(255),
  brand varchar(255),
  year_manufacture int4,
  mileage int4,
  last_maintenance timestamp(6),
  next_maintenance timestamp(6),
  state int4,
  state_date timestamp(6),
  next_state int4,
  planned_next_state timestamp(6),
  order_id int4,
  location int4,
  current_location int4,
  comment varchar(255),
  created_by VARCHAR(255),
  created_at TIMESTAMP,
  last_modified_by VARCHAR(255),
  last_modified_at TIMESTAMP,
  FOREIGN KEY (location) REFERENCES locations(id),
  FOREIGN KEY (current_location) REFERENCES locations(id),
  FOREIGN KEY (state) REFERENCES statuses(id),
  FOREIGN KEY (next_state) REFERENCES statuses(id)
);
CREATE TABLE cars_aud
( id int4,
  registration_number varchar(255),
  brand varchar(255),
  year_manufacture int4,
  mileage int4,
  last_maintenance timestamp(6),
  next_maintenance timestamp(6),
  state varchar(255),
  state_date timestamp(6),
  next_state varchar(255),
  planned_next_state timestamp(6),
  order_id int4,
  location int4,
  current_location int4,
  comment varchar(255),
  created_by VARCHAR(255),
  created_at TIMESTAMP,
  last_modified_by VARCHAR(255),
  last_modified_at TIMESTAMP,
  rev integer NOT NULL,
  revtype smallint,
  PRIMARY KEY ( id, rev )
);
CREATE TABLE revinfo
(
  id integer NOT NULL,
  timestamp bigint,
  CONSTRAINT revinfo_pkey PRIMARY KEY (id)
);

