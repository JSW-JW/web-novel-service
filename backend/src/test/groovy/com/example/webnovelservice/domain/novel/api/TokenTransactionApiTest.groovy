package com.example.webnovelservice.domain.novel.api

import com.example.webnovelservice.domain.common.ApiTest
import com.example.webnovelservice.domain.novel.steps.TokenTransactionSteps
import com.example.webnovelservice.domain.user.UserRepository
import com.example.webnovelservice.security.TokenProvider
import com.example.webnovelservice.security.UserPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

class TokenTransactionApiTest extends ApiTest {

    @Autowired
    TokenProvider tokenProvider

    @Autowired
    UserRepository userRepository

    static String jwtToken

    def generateJwtToken() {
        if (jwtToken == null) {
            def user = userRepository.findByEmail("user123@user.com").get()
            def userDetails = UserPrincipal.create(user);
            def authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            // generate jwt token for secured path api request
            jwtToken = tokenProvider.createToken(authentication);
        }
    }

    def "소장권 구매 요청 성공"() {
        given:
        jwtToken = generateJwtToken()
        def request = TokenTransactionSteps.getCreateTokenTransactionRequest()

        when:
        def response = TokenTransactionSteps.requestPurchaseToken(request, jwtToken)

        then:
        response.statusCode() == 200
        response.jsonPath().getLong("response.body.id") == 1
        response.jsonPath().getInt("response.body.tokensPurchased") == request.getTokensToCharge()
        response.jsonPath().getInt("response.body.price") != -1
    }


}
