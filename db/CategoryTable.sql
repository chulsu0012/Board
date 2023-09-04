create table CategoryTable(
    postId int not null auto_increment,
    postTagId int not null,
    postTitle char(20) not null,
    postDate char(20) char(20) not null,
    postContent text not null,
    postTripDays int not null,
    writerUserId int not null,
    primary key(postId)
);