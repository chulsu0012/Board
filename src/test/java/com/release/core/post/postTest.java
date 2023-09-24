package com.release.core.post;

import com.release.core.model.Post;
import com.release.core.model.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class postTest {

    @Test
    void postEqualOverrideTest() {
        Post post1a = new Post(1, new User());
        Post post1b = new Post(1, new User());
        Post post2 = new Post(2, new User());

        assertThat(post1a.equals(post1b)).isTrue();
        assertThat(post1a.equals(post2)).isFalse();
    }
}
