package com.example.webnovelservice.domain.novel.service

import com.example.webnovelservice.domain.novel.ChapterRepository
import com.example.webnovelservice.domain.novel.ChapterService
import com.example.webnovelservice.domain.novel.DatabaseCleanup
import com.example.webnovelservice.domain.novel.NovelRepository
import com.example.webnovelservice.model.command.RegisterChapterRequest
import com.example.webnovelservice.model.dto.ChapterDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@SpringBootTest
@ContextConfiguration
class ChapterServiceTest extends Specification {

    @Autowired
    ChapterService chapterService;

    @Autowired
    NovelServiceTest novelServiceTest;

    @Autowired
    DatabaseCleanup databaseCleanup;

    def setup() {
        databaseCleanup.afterPropertiesSet();
        databaseCleanup.execute();
    }

    def "should register chapter for novel"() {
        given:
        novelServiceTest.shouldregisternovel();

        Long novelId = 1L
        RegisterChapterRequest request = new RegisterChapterRequest(novelId, "Chapter 1", "Contents 1")
        ChapterDto expectedDto = new ChapterDto(1L, request.title(), request.contents(), novelId)

        when:
        ChapterDto actualDto = chapterService.registerChapterForNovel(request)

        then:
        actualDto == expectedDto
    }
}
