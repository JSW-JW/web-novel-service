package com.example.webnovelservice.domain.payment.steps;

import org.springframework.http.MediaType;

import com.example.webnovelservice.model.dto.request.CreatePurchaseRequest;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

class PurchaseSteps {

	static ExtractableResponse<Response> requestPurchaseChapter(CreatePurchaseRequest request, String jwtToken) {

		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(request)
			.header("Authorization", "Bearer " + jwtToken)
			.post("/api/v1/purchases")
			.then()
			.log().all().extract();
	}

	static CreatePurchaseRequest getCreatePurchaseChapterRequest(Long chapterId) {
		return new CreatePurchaseRequest(chapterId);
	}

	static CreatePurchaseRequest getBadFormedPurchaseChapterRequest() {
		return new CreatePurchaseRequest(null);
	}

}
