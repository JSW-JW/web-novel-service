package com.example.webnovelservice.domain.novel.api

import com.example.webnovelservice.domain.novel.common.ApiTest
import com.example.webnovelservice.domain.novel.steps.ChapterSteps
import com.example.webnovelservice.domain.novel.steps.NovelSteps
import com.example.webnovelservice.domain.user.UserRepository
import com.example.webnovelservice.exception.ResourceNotFoundException
import com.example.webnovelservice.security.TokenProvider
import com.example.webnovelservice.security.UserPrincipal
import io.restassured.RestAssured
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

class ChapterApiTest extends ApiTest{

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

    def "챕터 등록"() {
        given:
        generateJwtToken()

        // 챕터를 등록할 소설 생성
        def createNovelRequest = NovelSteps.getRegisterNovelRequest();
        NovelSteps.requestRegisterNovel(createNovelRequest, jwtToken);

        def createChapterRequest = ChapterSteps.getRegisterChapterRequest();

        when:
        // pass the request with the generated jwt token
        def response = ChapterSteps.requestRegisterChapter(createChapterRequest, jwtToken)

        then:

        response.statusCode() == HttpStatus.OK.value()
        response.jsonPath().getLong("response.body.id") == 1
        response.jsonPath().getString("response.body.title") == createChapterRequest.getTitle()
        response.jsonPath().getLong("response.body.order") == 1
        response.jsonPath().getString("response.body.contents") == createChapterRequest.getContents()
    }

    def "챕터 등록 시 소설이 존재하지 않는 경우 statusCode 404"() {
        given:
        generateJwtToken()
        def createChapterRequest = ChapterSteps.getRegisterChapterRequest();

        when:
        // pass the request with the generated jwt token
        def response = ChapterSteps.requestRegisterChapter(createChapterRequest, jwtToken)

        then:
        response.statusCode() == 404
    }
}
