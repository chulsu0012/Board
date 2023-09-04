create table BookmarkTable(
    bookmarkId int not null auto_increment,
    userId int not null,
    postId int not null,
    primary key(bookmarkId),
    foreign key(userId) references UserTable(userId),
    foreign key(postId) references postTable(postId)
);