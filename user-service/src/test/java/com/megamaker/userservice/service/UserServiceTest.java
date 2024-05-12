package com.megamaker.userservice.service;

import com.megamaker.userservice.repository.UserRepository;
import com.megamaker.userservice.vo.Provider;
import com.megamaker.userservice.vo.UserStatus;
import com.megamaker.userservice.dto.RequestRegisterUser;
import com.megamaker.userservice.dto.ResponseCheckUser;
import com.megamaker.userservice.dto.ResponseRegisterUser;
import com.megamaker.userservice.dto.ResponseUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.*;

@Slf4j
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
                .userId("test1234")
                .provider(Provider.NONE)
                .providerId("n12345678")
                .status(UserStatus.USER)
                .build();

        // when
        ResponseRegisterUser result = userService.register(requestRegisterUser);
        log.debug(result.toString());
        ResponseUser foundUser = userService.getUser(requestRegisterUser.getUserId());

        // then
        assertThat(result.getUserId()).isEqualTo(requestRegisterUser.getUserId());
        assertThat(foundUser.getUserId()).isEqualTo(requestRegisterUser.getUserId());
    }

    @Test
    public void 유저가_가입되어있지_않으면_각_멤버변수가_null을_반환한다() {
        // given
        RequestRegisterUser requestRegisterUser = RequestRegisterUser.builder()
                .providerId("n12345678")
                .build();

        // when
        ResponseCheckUser result = userService.isUserAlreadyRegistered(requestRegisterUser.getProviderId());

        // then
        assertThat(result.getProviderId()).isNull();
    }

    @Test
    public void 유저가_이미_가입되어있으면_통과한다() {
        // given
        RequestRegisterUser requestRegisterUser = RequestRegisterUser.builder()
                .providerId("n12345678")
                .build();

        // when
        userService.register(requestRegisterUser);
        ResponseCheckUser result = userService.isUserAlreadyRegistered(requestRegisterUser.getProviderId());

        // then
        assertThat(result.getProviderId()).isEqualTo(requestRegisterUser.getProviderId());
    }

    @Test
    public void 입력값이_제대로_주어지지_않으면_400_응답이_발생한다() {

    }
}