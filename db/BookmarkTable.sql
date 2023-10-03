create table BookmarkTable(
    bookmarkId BIGINT not null auto_increment,
    userId BIGINT not null,
    postId BIGINT not null,
    primary key(bookmarkId),
    foreign key(userId) references UserTable(userId),
    foreign key(postId) references postTable(postId)
);
