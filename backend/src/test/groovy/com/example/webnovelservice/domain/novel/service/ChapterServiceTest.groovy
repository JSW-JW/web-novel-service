package com.example.webnovelservice.domain.novel.service


import com.example.webnovelservice.domain.novel.ChapterService
import com.example.webnovelservice.domain.common.DatabaseCleanup
import com.example.webnovelservice.domain.novel.steps.ChapterSteps
import com.example.webnovelservice.exception.ResourceNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@SpringBootTest
@ContextConfiguration
class ChapterServiceTest extends Specification {

    @Autowired
    ChapterService chapterService;

    @Autowired
    DatabaseCleanup databaseCleanup;

    def setup() {
        databaseCleanup.afterPropertiesSet();
        databaseCleanup.execute();
    }

    def "챕터 등록 시 novelId 가 존재하지 않으면 ResourceNotFoundException"() {
        given:
        def createChapterRequest = ChapterSteps.getRegisterChapterRequest()

        when:
        chapterService.registerChapterForNovel(createChapterRequest)

        then:
        def e = thrown(ResourceNotFoundException)
        e.getMessage() == "Novel not found with novel id : '1'"
    }
}
