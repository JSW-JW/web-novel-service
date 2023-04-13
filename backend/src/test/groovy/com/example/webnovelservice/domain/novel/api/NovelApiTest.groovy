package com.example.webnovelservice.domain.novel.api;

import com.example.webnovelservice.domain.novel.ApiTest
import com.example.webnovelservice.domain.novel.NovelSteps
import com.example.webnovelservice.domain.user.UserRepository
import com.example.webnovelservice.security.TokenProvider
import com.example.webnovelservice.security.UserPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

class NovelApiTest extends ApiTest {

    @Autowired
    TokenProvider tokenProvider

    @Autowired
    UserRepository userRepository

    static String jwtToken

    def generateJwtToken() {
        if (jwtToken == null) {
            def user = userRepository.findById(1L).get()
            def userDetails = UserPrincipal.create(user);
            def authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            // generate jwt token for secured path api request
            jwtToken = tokenProvider.createToken(authentication);
        }
    }

    def "소설 등록"() {
        given:
        generateJwtToken()
        def request = NovelSteps.registerNovelRequest();
        def endpoint = "/api/v1/novels"

        when:
        // pass the request with the generated jwt token
        def response = NovelSteps.apiRequestRegisterNovel(request, endpoint, jwtToken);

        then:

        response.statusCode() == HttpStatus.OK.value()
        response.jsonPath().getLong("response.body.id") == 1
        response.jsonPath().getString("response.body.title") == request.getTitle()
        response.jsonPath().getString("response.body.description") == request.getDescription()
        response.jsonPath().getString("response.body.genre") == request.getGenre()
    }

}
