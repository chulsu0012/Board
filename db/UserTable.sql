drop table if exists UserTable CASCADE;
create table UserTable(
    userId bigint not null auto_increment,
    userEmail char(128) not null,
    userName char(128) not null,
    userPassword char(128) not null,
    userRegisterDate char(128) not null,
    userRole char(128) not null,
    primary key(userId)
);
