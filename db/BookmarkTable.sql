
create table BookmarkTable(
    bookmarkId bigint not null auto_increment,
    userId bigint not null,
    postId bigint not null,
    primary key(bookmarkId)
);
