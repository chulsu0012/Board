package com.release.core.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostEditFormDTO {

    private Long postId;
    private String postTitle;
    private String postDate;
    private String content;
    private Long tripDays;
    private List<Long> tagIdList;


    public PostEditFormDTO(Long postId, String postTitle, String postDate, String content, Long tripDays, List<Long> tagIdList) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postDate = postDate;
        this.content = content;
        this.tripDays = tripDays;
        this.tagIdList = tagIdList;
    }
}
