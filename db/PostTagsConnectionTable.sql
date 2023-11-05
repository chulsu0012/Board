create table PostTagsConnectionTable (
    connectionId bigint not null auto_increment,
    postId bigint not null,
    tagId bigint not null,
    primary key(connectionId)
);
