package com.release.core.post;

import com.release.core.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
