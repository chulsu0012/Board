package com.release.core.post;

import com.release.core.domain.Post;
import com.release.core.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional()
public class PostTest {

    @Autowired(required = true)
    PostRepository postRepository;

    @Test
    public void 글작성() throws Exception{
        Post post = new Post();
        post.setPostTitle("테스트 제목1");
        post.setPostContent("테스트 내용1");
        post.setPostDate("20231030");
        post.setPostTripDays(3L);
        post.setWriterUserId(1L);
        postRepository.save(post);

        Post post2 = new Post();
        post2.setPostTitle("테스트 제목2");
        post2.setPostContent("테스트 내용2");
        post2.setPostDate("20231030");
        post2.setPostTripDays(10L);
        post2.setWriterUserId(2L);
        postRepository.save(post2);

        Post postt = postRepository.findByWriterUserId(1L).get(0);
        assertThat(post.getPostId()).isEqualTo(postt.getPostId());
        assertThat(post.getPostTitle()).isEqualTo(postt.getPostTitle());
        assertThat(post2.getPostId()).isNotEqualTo(postt.getPostId());
        assertThat(post2.getPostTitle()).isNotEqualTo(postt.getPostTitle());
    }

}
