package com.release.core.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostWriteFormDTO {
    private String postTitle;
    private String postDate;
    private String content;
    private Long tripDays;
    private List<Long> tagIdList;

    public PostWriteFormDTO(String postTitle, String postDate, String content, Long tripDays, List<Long> tagIdList) {
        this.postTitle = postTitle;
        this.postDate = postDate;
        this.content = content;
        this.tripDays = tripDays;
        this.tagIdList = tagIdList;
    }
}
