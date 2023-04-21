package com.example.webnovelservice.domain.auth.api

import com.example.webnovelservice.domain.auth.steps.AuthSteps
import com.example.webnovelservice.domain.common.ApiTest
import com.example.webnovelservice.domain.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

class AuthApiTest extends ApiTest {

    @Autowired
    UserRepository userRepository;

    def "회원가입 성공"() {
        given:
        def name = "test-user-2";
        def email = "testemail@test.com";
        def password = 'test-password';

        // 유저 테이블은 각 테스트마다 databaseCleanup 을 진행하지 않으므로 예외처리 필요
        AuthSteps.deleteUserIfUserExists(userRepository, email);

        def request = AuthSteps.getSignupRequest(name, email, password);

        when:
        // 로그인 요청 전달
        def response = AuthSteps.requestSignup(request);

        then:
        response.statusCode() == HttpStatus.OK.value()
    }

    def "로그인 성공"() {
        given:
        def name = "test-user-2";
        def email = "testemail@test.com";
        def password = 'test-password';

        AuthSteps.createUserIfNotExists(userRepository, email, name, password)

        def request = AuthSteps.getLoginRequest(email, password);

        when:
        // 로그인 요청 전달
        def response = AuthSteps.requestLogin(request);

        then:
        response.jsonPath().getString("response.body.accessToken") != null
        response.jsonPath().getString("response.body.tokenType") == "Bearer"
        response.jsonPath().getInt("response.length") == 1
    }

    def "로그인 실패 - email 불일치"() {
        given:
        def name = "test-user-2";
        def email = "notmatched@notmatched.com";
        def password = 'test-password';

        def request = AuthSteps.getLoginRequest(email, password);

        when:
        def response = AuthSteps.requestLogin(request);

        then:
        response.statusCode() == HttpStatus.NOT_FOUND.value()
    }

    def "로그인 실패 - password 불일치"() {
        given:
        def name = "test-user-2";
        def email = "testemail@test.com";
        def password = 'test-password'

        def passwordNotMatched = 'never ever matching password';
        AuthSteps.createUserIfNotExists(userRepository, name, email, password)

        def request = AuthSteps.getLoginRequest(email, passwordNotMatched);

        when:
        def response = AuthSteps.requestLogin(request);

        then:
        response.statusCode() == HttpStatus.NOT_FOUND.value()
    }

    def "회원 가입 name 빈 값 들어올 경우 BadRequestException"() {
        given:
        def email = "testemail@test.com";
        def password = 'test-password';

        def request = AuthSteps.getSignupRequest(null, email, password);

        when:
        def response = AuthSteps.requestSignup(request);

        then:
        response.statusCode() == HttpStatus.BAD_REQUEST.value()
    }

    def "회원 가입 email 빈 값 들어올 경우 BadRequestException"() {
        given:
        def name = "test-user-2";
        def password = 'test-password';

        def request = AuthSteps.getSignupRequest(name, null, password);

        when:
        def response = AuthSteps.requestSignup(request);

        then:
        response.statusCode() == HttpStatus.BAD_REQUEST.value()
    }

    def "회원 가입 password 빈 값 들어올 경우 BadRequestException"() {
        given:
        def name = "test-user-2";
        def email = "testemail@test.com";

        def request = AuthSteps.getSignupRequest(name, email, null);

        when:
        def response = AuthSteps.requestSignup(request);

        then:
        response.statusCode() == HttpStatus.BAD_REQUEST.value()
    }
}
