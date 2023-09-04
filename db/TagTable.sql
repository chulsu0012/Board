create table TagTable(
    tagId int not null auto_increment,
    tagName char(20) not null,
    categoryId int not null,
    primary key(tagId),
    foreign key (categoryId) references CategoryTable(categoryId)
);