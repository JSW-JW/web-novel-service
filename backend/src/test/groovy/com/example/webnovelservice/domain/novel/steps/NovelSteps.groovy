package com.example.webnovelservice.domain.novel.steps

import com.example.webnovelservice.model.dto.request.CreateNovelRequest
import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.springframework.http.MediaType

class NovelSteps {

    static ExtractableResponse<Response> requestRegisterNovel(CreateNovelRequest request, String jwtToken) {

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .header("Authorization", "Bearer " + jwtToken)
                .post("/api/v1/novels")
                .then()
                .log().all().extract();
    }

    static CreateNovelRequest getRegisterNovelRequest() {
        final String title = "title 1";
        final String description = "description 1";
        final String genre = "genre 1";
        final Long authorId = 1L;
        new CreateNovelRequest(title, description, genre, authorId)
    }


}
