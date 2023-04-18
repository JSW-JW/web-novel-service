package com.example.webnovelservice.domain.novel.api

import com.example.webnovelservice.domain.common.ApiTest
import com.example.webnovelservice.domain.novel.steps.NovelSteps
import com.example.webnovelservice.domain.novel.steps.TokenTransactionSteps
import com.example.webnovelservice.domain.user.UserRepository
import com.example.webnovelservice.security.TokenProvider
import com.example.webnovelservice.security.UserPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

class TokenTransactionApiTest extends ApiTest {

    @Autowired
    TokenProvider tokenProvider

    @Autowired
    UserRepository userRepository

    static String jwtTokenOfUserID
    static String jwtTokenOfAuthorID

    def generateJwtTokenOfUserID() {
        if (jwtTokenOfUserID == null) {
            def user = userRepository.findByEmail("user123@user.com").get()
            def userDetails = UserPrincipal.create(user);
            def authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            // generate jwt for secured path api request
            jwtTokenOfUserID = tokenProvider.createToken(authentication);
        }
    }

    // 작가의 권한을 가진 jwt token 을 생성
    def generateJwtTokenOfAuthorID() {
        if (jwtTokenOfAuthorID == null) {
            def user = userRepository.findByEmail("author123@author.com").get()
            def userDetails = UserPrincipal.create(user);
            def authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            // generate jwt token for secured path api request
            jwtTokenOfAuthorID = tokenProvider.createToken(authentication);
        }
    }

    def "소장권 구매 요청 성공"() {
        given:
        jwtTokenOfUserID = generateJwtTokenOfUserID()
        jwtTokenOfAuthorID = generateJwtTokenOfAuthorID()

        // 소장권에 해당하는 소설 생성
        def createNovelRequest = NovelSteps.getRegisterNovelRequest();
        NovelSteps.requestRegisterNovel(createNovelRequest, jwtTokenOfAuthorID);

        def request = TokenTransactionSteps.getCreateTokenTransactionRequest()

        when:
        def response = TokenTransactionSteps.requestPurchaseToken(request, jwtTokenOfUserID)

        then:
        response.statusCode() == HttpStatus.OK.value()
        response.jsonPath().getLong("response.body.id") == 1
        response.jsonPath().getInt("response.body.tokensPurchased") == request.getTokensToCharge()
        response.jsonPath().getInt("response.body.price") != -1
    }

    def "작가는 소장권을 구매할 수 없다"() {
        given:
        jwtTokenOfAuthorID = generateJwtTokenOfAuthorID()

        // 소장권에 해당하는 소설 생성
        def createNovelRequest = NovelSteps.getRegisterNovelRequest();
        NovelSteps.requestRegisterNovel(createNovelRequest, jwtTokenOfAuthorID);

        def request = TokenTransactionSteps.getCreateTokenTransactionRequest()

        when:
        def response = TokenTransactionSteps.requestPurchaseToken(request, jwtTokenOfAuthorID)

        then:
        response.statusCode() == HttpStatus.FORBIDDEN.value()
    }
}
