package com.example.webnovelservice.domain.novel.service

import com.example.webnovelservice.domain.novel.common.DatabaseCleanup
import com.example.webnovelservice.domain.novel.NovelService
import com.example.webnovelservice.model.command.RegisterNovelRequest
import com.example.webnovelservice.model.dto.NovelDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@SpringBootTest
@ContextConfiguration
class NovelServiceTest extends Specification {

    @Autowired
    NovelService novelService;

    @Autowired
    DatabaseCleanup databaseCleanup;

    def setup() {
        databaseCleanup.afterPropertiesSet();
        databaseCleanup.execute();
    }

    def "should_register_novel"() {
        given:
        RegisterNovelRequest request = new RegisterNovelRequest("title 1", "description 1", "genre 1", 1L)
        NovelDto expectedDto = new NovelDto(1L, request.getTitle(), request.getGenre(), request.getDescription(), null)

        when:
        NovelDto actualDto = novelService.registerNovel(request)

        then:
        actualDto.getTitle() == expectedDto.getTitle()
        actualDto.getId() == expectedDto.getId()
        actualDto.getDescription() == expectedDto.getDescription()
    }
}
