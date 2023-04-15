package com.example.webnovelservice.domain.payment

import com.example.webnovelservice.domain.novel.NovelRepository
import com.example.webnovelservice.domain.novel.entity.Novel
import com.example.webnovelservice.domain.payment.entity.NovelTokenCounter
import com.example.webnovelservice.domain.payment.entity.OwnershipTokenTransaction
import com.example.webnovelservice.domain.user.UserRepository
import com.example.webnovelservice.domain.user.entity.User
import com.example.webnovelservice.model.dto.request.CreateTokenTransactionRequest

import spock.lang.Specification

class OwnershipTokenTransactionServiceTest extends Specification {
    def "chargeOwnershipToken should charge tokens and save transaction"() {
        given:
        def userRepository = Mock(UserRepository)
        def novelRepository = Mock(NovelRepository)
        def novelTokenCounterRepository = Mock(NovelTokenCounterRepository)
        def ownershipTokenTransactionRepository = Mock(OwnershipTokenTransactionRepository)
        def service = new OwnershipTokenTransactionService(
                ownershipTokenTransactionRepository,
                userRepository,
                novelRepository,
                novelTokenCounterRepository
        )

        def userId = 1L
        def novelId = 2L
        def tokensToCharge = 5
        def user = new User(id: userId, name: "test-user")
        userRepository.findById(userId) >> Optional.of(user)
        def novel = new Novel(id: novelId, title: "test-title")
        novelRepository.findById(novelId) >> Optional.of(novel)
        def price = 1500
        TokenPolicyService.getPrice(tokensToCharge, novelId) >> price

        when:
        service.chargeOwnershipToken(userId, new CreateTokenTransactionRequest(novelId: novelId, tokensToCharge: tokensToCharge))

        then:
        1 * userRepository.findById(userId)
        1 * novelRepository.findById(novelId)
        1 * TokenPolicyService.getPrice(tokensToCharge, novelId)
        1 * ownershipTokenTransactionRepository.save(_ as OwnershipTokenTransaction)
        1 * novelTokenCounterRepository.findByUserIdAndNovelId(userId, novelId)
        1 * novelTokenCounterRepository.save(_ as NovelTokenCounter)
    }
}

