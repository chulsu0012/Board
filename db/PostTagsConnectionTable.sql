create table PostTagsConnectionTable (
    connectionId int not null auto_increment,
    postId char(20) not null,
    tagId int not null,
    primary key(connectionId),
    foreign key (postId) references PostTable(postId),
    foreign key (tagId) references TagTable(tagId),
);