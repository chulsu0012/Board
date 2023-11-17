drop table if exists UserTable CASCADE;
create table UserTable(
    userId bigint not null auto_increment,
    userName char(255) not null,
    userEmail char(255) not null,
    userPassword char(128) not null,
    userRegisterDate char(20) not null,
    userIsAdmin bigint not null,
    primary key(userId)
);
