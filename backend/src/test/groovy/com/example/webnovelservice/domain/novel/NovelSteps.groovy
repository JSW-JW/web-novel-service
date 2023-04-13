package com.example.webnovelservice.domain.novel

import com.example.webnovelservice.model.command.RegisterNovelRequest
import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.springframework.http.MediaType

class NovelSteps {

    static ExtractableResponse<Response> apiRequestRegisterNovel(RegisterNovelRequest request, String endpoint, String jwtToken) {

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .header("Authorization", "Bearer " + jwtToken)
                .post(endpoint)
                .then()
                .log().all().extract();
    }

    static RegisterNovelRequest registerNovelRequest() {
        final String title = "title 1";
        final String description = "description 1";
        final String genre = "genre 1";
        final Long authorId = 1L;
        new RegisterNovelRequest(title, description, genre, authorId)
    }


}
