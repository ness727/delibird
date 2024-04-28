package com.megamaker.userservice.service;

import com.megamaker.userservice.Repository.UserRepository;
import com.megamaker.userservice.domain.User;
import com.megamaker.userservice.dto.RequestRegisterUser;
import com.megamaker.userservice.dto.ResponseRegisterUser;
import com.megamaker.userservice.dto.ResponseUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserServiceTest {
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceTest(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userService = new UserService(this.userRepository);
    }

    @Test
    public void 저장이_성공적으로_완료된다() {
        // given
        RequestRegisterUser requestRegisterUser = RequestRegisterUser.builder()
                .userId("test@test.com")
                .providerId("n12345678")
                .status(User.Status.USER)
                .build();

        // when
        ResponseRegisterUser result = userService.register(requestRegisterUser);
        ResponseUser foundUser = userService.getUser(requestRegisterUser.getUserId());

        // then
        Assertions.assertThat(result.getUserId()).isEqualTo(requestRegisterUser.getUserId());
        Assertions.assertThat(foundUser.getUserId()).isEqualTo(requestRegisterUser.getUserId());
    }
}