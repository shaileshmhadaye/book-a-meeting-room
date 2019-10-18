--create table if not exists persistent_logins (
--     username varchar(100) not null,
--     series varchar(64) primary key,
--     token varchar(64) not null,
--     last_used timestamp not null
--);
--
delete from  person_role;
delete from  role;
delete from  person;

INSERT INTO role (role_id, role) VALUES
(1, 'User');
--(2, 'ACTUATOR'),
--(3, 'USER');

--INSERT INTO person (id, email, password) VALUES
--(1, 'admin@gmail.com', '1234');
--(3, 'user@gmail.com', '2345', 'User');

--insert into person_role(person_id, role_id) values
--(1,1),
--(1,2),
--(1,3),
--(3,2);
