package com.release.core.repository;

import com.release.core.domain.Bookmark;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public class SQLBookmarkRepository implements BookmarkRepository {

    @Autowired
    SqlSession SqlSession;

    //등록
    public void doBookmark(Bookmark bookmark) {

    }

    //조회
    public void loadBookmark(Bookmark bookmark) {

    }

    //삭제
    public void deleteBookmark(int bookmarkId, int userId) {

    }

}
