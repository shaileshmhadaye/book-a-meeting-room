--create table if not exists persistent_logins (
--     username varchar(100) not null,
--     series varchar(64) primary key,
--     token varchar(64) not null,
--     last_used timestamp not null
--);
--
--delete from  person_role;
delete from  role;
delete from  person;

INSERT INTO role (id, role) VALUES
(11, 'User');
--(2, 'ACTUATOR'),
--(3, 'USER');

--delete from person where person_id=3;
--delete from person_role where person_id=3;

insert into person(person_id, department, email, first_name, last_name, location, password, active, role_id) values(3, "testing", "test@g.com", "test", "test", "mumbai", "$2a$10$yZMQfTjCQKLyjdMH1j8B/ewJFiPPru0/HrX53p3Hjk.XYAtDxTv6W", 1, 11);
--insert into person_role(person_id, role_id) values(3,112);

--INSERT INTO person (id, email, password) VALUES
--(1, 'admin@gmail.com', '1234');
--(3, 'user@gmail.com', '2345', 'User');

--insert into person_role(person_id, role_id) values
--(1,1),
--(1,2),
--(1,3),
--(3,2);
