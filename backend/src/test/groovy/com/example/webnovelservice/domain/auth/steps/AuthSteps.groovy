package com.example.webnovelservice.domain.auth.steps

import com.example.webnovelservice.domain.user.UserRepository
import com.example.webnovelservice.model.dto.request.LoginRequest
import com.example.webnovelservice.model.dto.request.SignUpRequest
import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.springframework.http.MediaType

class AuthSteps {

    static void deleteUserIfUserExists(UserRepository userRepository, String email) {
        def userOptional = userRepository.findByEmail(email)
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
        }
    }

    static void createUserIfNotExists(UserRepository userRepository, name, email, password) {
        def userOptional = userRepository.findByEmail(email)
        if (userOptional.isEmpty()) {
            def signupRequest = getSignupRequest(name, email, password);
            requestSignup(signupRequest);
        }
    }

    static ExtractableResponse<Response> requestSignup(SignUpRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .post("/api/v1/auth/signup")
                .then()
                .log().all().extract();
    }

    static ExtractableResponse<Response> requestLogin(LoginRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .post("/api/v1/auth/login")
                .then()
                .log().all().extract();
    }

    static SignUpRequest getSignupRequest(String name, String email, String password) {
        return new SignUpRequest(name, email, password);
    }

    static LoginRequest getLoginRequest(String email, String name) {
        return new LoginRequest(email, name);
    }
}
