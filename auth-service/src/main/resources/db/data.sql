INSERT INTO role (id, role_name, description) VALUES (1, 'ADMIN', 'Администратор');
INSERT INTO role (id, role_name, description) VALUES (2, 'SERVICE_MANAGER', 'Менеджер по обслуживанию ');
INSERT INTO role (id, role_name, description) VALUES (3, 'RENT_MANAGER', 'Менеджер по прокату');
INSERT INTO role (id, role_name, description) VALUES (4, 'BOSS', 'Руководитель');
INSERT INTO users VALUES(1,'admin', 'Admin', 'Admin', '$2a$04$5BabAtl1XfUw4Iqr0MaY2OZid8g7JT3wv.FlK9wPgnYZQO1JD6l4W','admin', '1992-01-01',	'2018-05-01','Пункт обслуживания\ГО', 1);
INSERT INTO users VALUES(2,'Иван','Иванов',	'Иванович',	'$2a$10$mDLaigoBbwJm35bq.BboReaYoP3Wd.eQ51Ru7Q4pRtvB5qWtPqkny',	'RENT_MANAGER',	'1992-01-01',	'2018-05-01',	'Пункт проката №1'	,3);
INSERT INTO users VALUES(3,'Пётр','Петров','Петрович',	'$2a$10$uKdVY01YPgiCpBIvBnblgen6OWqttaYvA1.SkDu6nopKc7FTdtFzK',	'SERVICE_MANAGER',	'1989-05-05',	'2017-12-05','Пункт обслуживания\ГО',2);
INSERT INTO users VALUES(4,'Абрам','Робинович','Моисеевич','$2a$10$noE3YXwtUnIoCNfVYEbTNO1rk0R35wRkevDGun3IbxKl5DbazQho6','BOSS',	'1972-03-02',	'2016-10-04','Пункт обслуживания\ГО' ,4);