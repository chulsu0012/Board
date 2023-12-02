package com.release.core.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Entity
@Table(name="POSTTABLE")
public class Post {

    @Column(name="POSTID") @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(name = "POSTTITLE")
    private String postTitle;

    @Column(name = "POSTDATE")
    private String postDate;

    @Column(name = "POSTCONTENT")
    private String postContent;

    @Column(name = "POSTTRIPDAYS")
    private Long postTripDays;

    @Column(name="WRITERUSERID")
    private Long writerUserId;

    @Transient
    private List<Long> tagIdList;

}
