package com.release.core.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name="TAGTABLE")
public class Tag {

    @Column(name="TAGID") @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @Column(name="TAGNAME")
    private String tagName;

    @Column(name="CATEGORYID")
    private Long tagParentId;

}
