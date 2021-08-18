USE Arcadia;

drop table IF EXISTS highscore;
CREATE TABLE highscore(
	hid int(10) NOT NULL AUTO_INCREMENT,
	game varchar(20),
    user varchar(20),
    score int(100),
    groupNo int(10),
    
    foreign key(user) references users(user_name),
    primary key(hid)
);