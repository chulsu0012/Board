create table TagTable(
    tagId bigint not null auto_increment,
    tagName char(20) not null,
    categoryId bigint not null,
    primary key(tagId),
    foreign key (categoryId) references CategoryTable(categoryId)
);
