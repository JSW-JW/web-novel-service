package com.example.webnovelservice.domain.payment.steps

import com.example.webnovelservice.model.dto.request.CreateTokenTransactionRequest
import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.springframework.http.MediaType

class TokenTransactionSteps {

    static ExtractableResponse<Response> requestPurchaseToken(CreateTokenTransactionRequest request, String jwtToken) {

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .header("Authorization", "Bearer " + jwtToken)
                .post("/api/v1/transactions")
                .then()
                .log().all().extract();
    }

    static CreateTokenTransactionRequest getCreateTokenTransactionRequest(Integer tokensToCharge) {
        final Long novelId = 1L
        new CreateTokenTransactionRequest(novelId, tokensToCharge)
    }
}
