package com.example.webnovelservice.domain.novel.api

import com.example.webnovelservice.domain.common.ApiTest
import com.example.webnovelservice.domain.novel.steps.ChapterSteps
import com.example.webnovelservice.domain.novel.steps.NovelSteps
import com.example.webnovelservice.domain.payment.steps.PurchaseSteps
import com.example.webnovelservice.domain.payment.steps.TokenTransactionSteps
import com.example.webnovelservice.domain.user.UserRepository
import com.example.webnovelservice.security.TokenProvider
import com.example.webnovelservice.security.UserPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

class ChapterApiTest extends ApiTest{

    @Autowired
    TokenProvider tokenProvider

    @Autowired
    UserRepository userRepository

    static String jwtTokenOfUserID
    static String jwtTokenOfAuthorID

    def generateJwtTokenOfUserID() {
        def user = userRepository.findByEmail("user123@user.com").get()
        def userDetails = UserPrincipal.create(user);
        def authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // generate jwt for secured path api request
        jwtTokenOfUserID = tokenProvider.createToken(authentication);
    }

    // 작가의 권한을 가진 jwt token 을 생성
    def generateJwtTokenOfAuthorID() {
        def user = userRepository.findByEmail("author123@author.com").get()
        def userDetails = UserPrincipal.create(user);
        def authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // generate jwt token for secured path api request
        jwtTokenOfAuthorID = tokenProvider.createToken(authentication);
    }

    def "챕터 등록"() {
        given:
        generateJwtTokenOfAuthorID()

        // 챕터를 등록할 소설 생성
        def createNovelRequest = NovelSteps.getRegisterNovelRequest();
        NovelSteps.requestRegisterNovel(createNovelRequest, jwtTokenOfAuthorID);

        // 챕터 생성
        def tokensRequired = 1
        def createChapterRequest = ChapterSteps.getRegisterChapterRequest(tokensRequired);

        when:
        // send request with the generated jwt token
        def response = ChapterSteps.requestRegisterChapter(createChapterRequest, jwtTokenOfAuthorID)

        then:

        response.statusCode() == HttpStatus.OK.value()
        response.jsonPath().getLong("response.body.id") == 1
        response.jsonPath().getString("response.body.title") == createChapterRequest.getTitle()
        response.jsonPath().getLong("response.body.order") == 1
        response.jsonPath().getString("response.body.contents") == createChapterRequest.getContents()
    }

    def "챕터 등록 시 소설이 존재하지 않는 경우 404 Not Found"() {
        given:
        generateJwtTokenOfAuthorID()

        def tokensRequired = 1
        def createChapterRequest = ChapterSteps.getRegisterChapterRequest(tokensRequired);

        when:
        // send request with the generated jwt token
        def response = ChapterSteps.requestRegisterChapter(createChapterRequest, jwtTokenOfAuthorID)

        then:
        response.statusCode() == HttpStatus.NOT_FOUND.value()
    }

    def "구매한 챕터는 조회할 수 있어야 한다"() {
        given:
        jwtTokenOfUserID = generateJwtTokenOfUserID()
        jwtTokenOfAuthorID = generateJwtTokenOfAuthorID()

        // 구매할 챕터에 해당하는 소설 생성
        def createNovelRequest = NovelSteps.getRegisterNovelRequest()
        NovelSteps.requestRegisterNovel(createNovelRequest, jwtTokenOfAuthorID)

        // 구매할 챕터 생성
        def tokensRequired = 1
        def createChapterRequest = ChapterSteps.getRegisterChapterRequest(tokensRequired)
        ChapterSteps.requestRegisterChapter(createChapterRequest, jwtTokenOfAuthorID)

        // 소장권 구매
        def tokensToCharge = 1
        def createTransactionRequest = TokenTransactionSteps.getCreateTokenTransactionRequest(tokensToCharge)
        TokenTransactionSteps.requestPurchaseToken(createTransactionRequest, jwtTokenOfUserID)

        // 챕터 구매
        def chapterId = 1L
        def purchaseChapterRequest = PurchaseSteps.getCreatePurchaseChapterRequest(chapterId)
        PurchaseSteps.requestPurchaseChapter(purchaseChapterRequest, jwtTokenOfUserID)

        // 챕터 조회 요청
        when:
        def response = ChapterSteps.requestReadChapter(chapterId, jwtTokenOfUserID)

        then:
        response.statusCode() == HttpStatus.OK.value();
        response.jsonPath().getLong("response.body.id") == 1
        response.jsonPath().getString("response.body.title") == createChapterRequest.getTitle()
        response.jsonPath().getLong("response.body.order") == 1
        response.jsonPath().getString("response.body.contents") == createChapterRequest.getContents()
    }

    def "구매하지 않은 챕터는 조회할 수 없어야 한다."() {
        given:
        jwtTokenOfUserID = generateJwtTokenOfUserID()
        jwtTokenOfAuthorID = generateJwtTokenOfAuthorID()

        // 구매할 챕터에 해당하는 소설 생성
        def createNovelRequest = NovelSteps.getRegisterNovelRequest()
        NovelSteps.requestRegisterNovel(createNovelRequest, jwtTokenOfAuthorID)

        // 구매할 챕터 생성
        def tokensRequired = 1
        def createChapterRequest = ChapterSteps.getRegisterChapterRequest(tokensRequired)
        ChapterSteps.requestRegisterChapter(createChapterRequest, jwtTokenOfAuthorID)

        def chapterId = 1L

        when:
        def response = ChapterSteps.requestReadChapter(chapterId, jwtTokenOfUserID)

        then:
        response.statusCode() == HttpStatus.NOT_FOUND.value();
    }
}
