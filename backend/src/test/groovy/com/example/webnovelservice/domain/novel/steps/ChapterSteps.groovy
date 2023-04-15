package com.example.webnovelservice.domain.novel.steps

import com.example.webnovelservice.model.command.RegisterChapterRequest
import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.springframework.http.MediaType

class ChapterSteps {
    static ExtractableResponse<Response> requestRegisterChapter(RegisterChapterRequest request, String jwtToken) {

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .header("Authorization", "Bearer " + jwtToken)
                .post("/api/v1/chapters")
                .then()
                .log().all().extract();
    }

    static RegisterChapterRequest getRegisterChapterRequest() {
        final Long novelId = 1L
        final String title = "test-title";
        final String contents = "test-contents"
        new RegisterChapterRequest(novelId, title, contents)
    }
}
