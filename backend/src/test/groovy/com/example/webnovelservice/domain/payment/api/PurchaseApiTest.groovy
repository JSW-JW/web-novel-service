package com.example.webnovelservice.domain.payment.api

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


class PurchaseApiTest extends ApiTest {

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

    def "챕터 구매 요청 성공"() {
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

        // 구매 요청 준비
        def chapterId = 1L;
        def request = PurchaseSteps.getCreatePurchaseChapterRequest(chapterId)

        when:
        def response = PurchaseSteps.requestPurchaseChapter(request, jwtTokenOfUserID)

        then:
        response.statusCode() == HttpStatus.OK.value()
        response.jsonPath().getLong("response.body.id") == 1L
        response.jsonPath().getLong("response.body.tokensLeft") == 0L
        response.jsonPath().getString("response.body.chapter.title") == "test-title"
        response.jsonPath().getString("response.body.chapter.contents") == "test-contents"
        response.jsonPath().getLong("response.body.chapter.order") == 1L
        response.jsonPath().getLong("response.body.chapter.novelId") == 1L
    }

    def "소장권을 구매하지 않았을 경우 InsufficientTokenException"() {
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

        def chapterId = 1L;

        def request = PurchaseSteps.getCreatePurchaseChapterRequest(chapterId)

        when:
        def response = PurchaseSteps.requestPurchaseChapter(request, jwtTokenOfUserID)

        then:
        response.statusCode() == HttpStatus.BAD_REQUEST.value()
    }

    def "소장권 갯수가 부족한 경우 InsufficientTokenException"() {
        given:
        jwtTokenOfUserID = generateJwtTokenOfUserID()
        jwtTokenOfAuthorID = generateJwtTokenOfAuthorID()

        // 구매할 챕터에 해당하는 소설 생성
        def createNovelRequest = NovelSteps.getRegisterNovelRequest()
        NovelSteps.requestRegisterNovel(createNovelRequest, jwtTokenOfAuthorID)

        // 구매할 챕터 생성
        def tokensRequired = 5
        def createChapterRequest = ChapterSteps.getRegisterChapterRequest(tokensRequired)
        ChapterSteps.requestRegisterChapter(createChapterRequest, jwtTokenOfAuthorID)

        // 소장권 구매
        def tokensToCharge = 1
        def createTransactionRequest = TokenTransactionSteps.getCreateTokenTransactionRequest(tokensToCharge)
        TokenTransactionSteps.requestPurchaseToken(createTransactionRequest, jwtTokenOfUserID)

        def chapterId = 1L;
        def request = PurchaseSteps.getCreatePurchaseChapterRequest(chapterId)

        when:
        def response = PurchaseSteps.requestPurchaseChapter(request, jwtTokenOfUserID)

        then:
        response.statusCode() == HttpStatus.BAD_REQUEST.value()
    }

    def "chapterId 가 null 일 경우 BadRequestException"() {
        given:
        jwtTokenOfUserID = generateJwtTokenOfUserID()
        jwtTokenOfAuthorID = generateJwtTokenOfAuthorID()

        // 구매할 챕터에 해당하는 소설 생성
        def createNovelRequest = NovelSteps.getRegisterNovelRequest()
        NovelSteps.requestRegisterNovel(createNovelRequest, jwtTokenOfAuthorID)

        // 구매할 챕터 생성
        def createChapterRequest = ChapterSteps.getRegisterChapterRequest()
        ChapterSteps.requestRegisterChapter(createChapterRequest, jwtTokenOfAuthorID)

        def request = PurchaseSteps.getBadFormedPurchaseChapterRequest()

        when:
        def response = PurchaseSteps.requestPurchaseChapter(request, jwtTokenOfUserID)

        then:
        response.statusCode() == HttpStatus.BAD_REQUEST.value()
    }

    def "작가는 유저 api 를 통해 챕터를 구매할 수 없다"() {
        given:
        jwtTokenOfUserID = generateJwtTokenOfUserID()
        jwtTokenOfAuthorID = generateJwtTokenOfAuthorID()

        // 구매할 챕터에 해당하는 소설 생성
        def createNovelRequest = NovelSteps.getRegisterNovelRequest()
        NovelSteps.requestRegisterNovel(createNovelRequest, jwtTokenOfAuthorID)

        // 구매할 챕터 생성
        def createChapterRequest = ChapterSteps.getRegisterChapterRequest()
        ChapterSteps.requestRegisterChapter(createChapterRequest, jwtTokenOfAuthorID)

        def chapterId = 1L;

        def request = PurchaseSteps.getCreatePurchaseChapterRequest(chapterId)

        when:
        def response = PurchaseSteps.requestPurchaseChapter(request, jwtTokenOfAuthorID)

        then:
        response.statusCode() == HttpStatus.FORBIDDEN.value()
    }
}
