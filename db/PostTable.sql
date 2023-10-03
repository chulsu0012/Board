create table PostTable(
    postId bigint not null auto_increment,
    postTitle char(20) not null,
    postDate char(20) not null,
    postContent text not null,
    postTripDays bigint not null,
    writerUserId bigint not null,
    primary key(postId),
    foreign key(writerUserId) references UserTable(userId)
);
