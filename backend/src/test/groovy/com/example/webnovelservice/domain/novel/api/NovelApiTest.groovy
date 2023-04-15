package com.example.webnovelservice.domain.novel.api;

import com.example.webnovelservice.domain.novel.common.ApiTest
import com.example.webnovelservice.domain.novel.steps.NovelSteps
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

    static String jwtTokenOfUserID
    static String jwtTokenOfAuthorID

    def generateJwtToken() {
        if (jwtTokenOfUserID == null) {
            def user = userRepository.findByEmail("user123@user.com").get()
            def userDetails = UserPrincipal.create(user);
            def authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            // generate jwt for secured path api request
            jwtTokenOfUserID = tokenProvider.createToken(authentication);
        }
    }

    // 작가의 권한을 가진 jwt token 을 생성
    def generateJwtTokenOfAuthorId() {
        if (jwtTokenOfAuthorID == null) {
            def user = userRepository.findByEmail("author123@author.com").get()
            def userDetails = UserPrincipal.create(user);
            def authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            // generate jwt token for secured path api request
            jwtTokenOfAuthorID = tokenProvider.createToken(authentication);
        }
    }

    def "소설 등록"() {
        given:
        generateJwtTokenOfAuthorId()
        def request = NovelSteps.getRegisterNovelRequest();

        when:
        // pass the request with the generated jwt token
        def response = NovelSteps.requestRegisterNovel(request, jwtTokenOfAuthorID);

        then:

        response.statusCode() == HttpStatus.OK.value()
        response.jsonPath().getLong("response.body.id") == 1
        response.jsonPath().getString("response.body.title") == request.getTitle()
        response.jsonPath().getString("response.body.description") == request.getDescription()
        response.jsonPath().getString("response.body.genre") == request.getGenre()
    }

    def "로그인 하지 않고 소설 등록을 할 수 없다"() {
        given:
        def request = NovelSteps.getRegisterNovelRequest();

        when:
        def response = NovelSteps.requestRegisterNovel(request, null)

        then:
        response.statusCode() == HttpStatus.UNAUTHORIZED.value()
    }

    def "작가가 아니면 소설 등록을 할 수 없다"() {
        given:
        generateJwtToken()
        def request = NovelSteps.getRegisterNovelRequest();

        when:
        def response = NovelSteps.requestRegisterNovel(request, jwtTokenOfUserID)

        then:
        response.statusCode() == HttpStatus.FORBIDDEN.value()
    }

}
