package com.example.webnovelservice.domain.payment;

public class TokenPolicyService {

	public static Integer getPrice(Integer tokensToCharge, Long novelId) {
		// TODO: 소설 별 가격 정책 적용
		Integer rate = 300;
		return tokensToCharge * rate;
	}
}
