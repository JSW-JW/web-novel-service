package com.example.webnovelservice.domain.novel.steps

import com.example.webnovelservice.model.command.RegisterNovelRequest
import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.springframework.http.MediaType

class NovelSteps {

    static ExtractableResponse<Response> requestRegisterNovel(RegisterNovelRequest request, String jwtToken) {

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .header("Authorization", "Bearer " + jwtToken)
                .post("/api/v1/novels")
                .then()
                .log().all().extract();
    }

    static RegisterNovelRequest getRegisterNovelRequest() {
        final String title = "title 1";
        final String description = "description 1";
        final String genre = "genre 1";
        final Long authorId = 1L;
        new RegisterNovelRequest(title, description, genre, authorId)
    }


}
