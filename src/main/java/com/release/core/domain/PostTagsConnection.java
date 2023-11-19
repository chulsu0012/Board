package com.release.core.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name="POSTTAGSCONNECTIONTABLE")
public class PostTagsConnection {

    @Column(name="CONNECTIONID") @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long connectionId;

    @Column(name="POSTID")
    private Long postId;

    @Column(name="TAGID")
    private Long tagId;

}
