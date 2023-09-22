package com.release.core.post.tag;

import com.release.core.post.tag.Category;

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
