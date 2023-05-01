package com.example.webnovelservice.chapter.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webnovelservice.chapter.domain.entity.Chapter;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
	List<Chapter> findByNovelId(Long novelId);

	Integer countByNovelId(Long novelId);
}
