package com.release.core.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name="CATEGORYTABLE")
public class Category {

    @Column(name="CATEGORYID") @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(name="CATEGORYNAME")
    private String categoryName;

}
