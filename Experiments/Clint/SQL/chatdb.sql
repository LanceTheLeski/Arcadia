USE Arcadia;

drop table IF EXISTS chat_log;
drop table IF EXISTS chatLog;
CREATE TABLE chat_log(
	id int(10) NOT NULL AUTO_INCREMENT,
	user varchar(20),
    message varchar(120),
    date DATETIME,
    
    foreign key(user) references users(user_name),
    primary key(id)
);