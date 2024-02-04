package com.release.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name="BOOKMARKTABLE")
public class Bookmark {

    @Column(name="BOOKMARKID")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookmarkId;
    @Column(name="USERID")
    private Long userId;
    @Column(name="POSTID")
    private Long postId;
}
