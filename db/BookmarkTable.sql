create table BookmarkTable(
    bookmarkId int not null auto_increment,
    userId int not null,
    postId int not null,
    primary key(bookmarkId)
);