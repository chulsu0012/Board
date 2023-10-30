package com.release.core.domain;

public class Tag {

    private int id;
    private String name;
    private Category parent;

    public Tag(int id, String name, Category parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
    }

}
