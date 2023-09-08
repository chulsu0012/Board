create table PostTable(
    postId int not null auto_increment,
    postTitle char(20) not null,
    postDate char(20) char(20) not null,
    postContent text not null,
    postTripDays int not null,
    writerUserId int not null,
    primary key(postId),
    foreign key(writerUserId) references UserTable(userId);
);