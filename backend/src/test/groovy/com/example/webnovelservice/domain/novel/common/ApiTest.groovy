package com.example.webnovelservice.domain.novel.common

import com.example.webnovelservice.domain.user.UserRepository
import com.example.webnovelservice.domain.user.entity.User
import com.example.webnovelservice.model.enums.AuthProvider
import com.example.webnovelservice.model.enums.UserRole
import io.restassured.RestAssured
import org.spockframework.spring.EnableSharedInjection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.test.context.ContextConfiguration
import spock.lang.Shared
import spock.lang.Specification

@ContextConfiguration
@AutoConfigureMockMvc
@EnableSharedInjection
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiTest extends Specification {

    @Autowired
    @Shared
    UserRepository userRepository

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @LocalServerPort
    private int port;

    def setupSpec() {
        try {
            def user = new User(name: "test-name", emailVerified: true, email: "user123@user.com",
                    password: "password", provider: AuthProvider.local, userRole: UserRole.USER)
            userRepository.save(user)

            def author = new User(name: "test-name", emailVerified: true, email: "author123@author.com",
                    password: "password", provider: AuthProvider.local, userRole: UserRole.AUTHOR)
            userRepository.save(author)

        } catch (DataIntegrityViolationException e) {
            // log the exception and ignore.
        }
    }

    def setup() {
        if(RestAssured.port == RestAssured.UNDEFINED_PORT) {
            RestAssured.port = port;
            databaseCleanup.afterPropertiesSet();
        }
        databaseCleanup.execute();
    }
}
