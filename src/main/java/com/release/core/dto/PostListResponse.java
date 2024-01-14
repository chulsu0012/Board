package com.release.core.dto;

import com.release.core.domain.Post;
import lombok.Data;

import java.util.List;

@Data
public class PostListResponse {

    private int pageNum=0;
    private List<Post> postList;

    public PostListResponse(int pageNum, List<Post> postList) {
        this.pageNum = pageNum;
        this.postList = postList;
    }

}
