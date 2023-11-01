package com.release.core.repository;

import com.release.core.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
class MemoryUserRepositoryTest {

    MemoryUserRepository repository = new MemoryUserRepository();
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }
    @Test
    public void save() {
//given
        User user = new User();
        user.setName("spring");
//when
        repository.save(user);
//then
        User result = repository.findById(user.getId()).get();
        assertThat(result).isEqualTo(user);
    }
    @Test
    public void findByName() {
//given
        User member1 = new User();
        member1.setName("spring1");

        repository.save(member1);

        User member2 = new User();
        member2.setName("spring2");

        repository.save(member2);
//when
        User result = repository.findByName("spring1").get();
//then
        assertThat(result).isEqualTo(member1);
    }
    @Test
    public void findAll() {
//given
        User member1 = new User();
        member1.setName("spring1");
        repository.save(member1);
        User member2 = new User();
        member2.setName("spring2");
        repository.save(member2);
//when
        List<User> result = repository.findAll();
//then
        assertThat(result.size()).isEqualTo(2);
    }
}
