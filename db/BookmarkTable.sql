create table BookmarkTable(
    bookmarkId bigint not null auto_increment,
    userId bigint not null,
    postId bigint not null,
    primary key(bookmarkId),
    foreign key(userId) references UserTable(userId),
    foreign key(postId) references postTable(postId)
);
