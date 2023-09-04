create table UserTable(
    userId int not null auto_increment,
    userName char(20) not null,
    userEmail char(20) not null,
    userPassword char(20) not null,
    userRegisterDate char(20) not null,
    userIsAdmin int not null,
    primary key(userId)
);