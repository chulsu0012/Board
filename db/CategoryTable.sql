drop table if exists CategoryTable CASCADE;
create table CategoryTable(
    categoryId bigint not null auto_increment,
    categoryName char(20) not null,
    primary key(categoryId)
);
