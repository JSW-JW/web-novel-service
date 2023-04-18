package com.example.webnovelservice.domain.novel.steps

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

    static CreateTokenTransactionRequest getCreateTokenTransactionRequest() {
        final Long novelId = 1L
        final Integer tokensToCharge = 5
        new CreateTokenTransactionRequest(novelId, tokensToCharge)
    }
}
