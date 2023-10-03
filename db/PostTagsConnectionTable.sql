create table PostTagsConnectionTable (
    connectionId bigint not null auto_increment,
    postId bigint not null,
    tagId bigint not null,
    primary key(connectionId),
    foreign key (postId) references PostTable(postId),
    foreign key (tagId) references TagTable(tagId)
);
