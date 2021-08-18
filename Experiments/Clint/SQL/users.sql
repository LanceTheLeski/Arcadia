USE Arcadia;

drop table IF EXISTS users;
CREATE TABLE users(
	user_name varchar(20) unique,
	first_name varchar(20),
    last_name varchar(20),
    password varchar(30),
    groupNo int(10),
    
    primary key(user_name)
);