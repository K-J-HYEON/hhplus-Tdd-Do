package io.hhplus.tdd.respository;

import io.hhplus.tdd.repository.UserPointRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserPointRepositoryTest {

    @Autowired
    private UserPointRepository userPointRepository;

    @Test
    public void UserPointRepositoryIsNotNull() {
        assertThat(userPointRepository).isNotNull();
    }
}
