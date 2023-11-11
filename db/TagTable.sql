drop table if exists TagTable CASCADE;
create table TagTable(
    tagId bigint not null auto_increment,
    tagName char(20) not null,
    categoryId bigint not null,
    primary key(tagId)
);
