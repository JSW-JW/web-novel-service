package com.example.webnovelservice.domain.novel;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webnovelservice.model.entity.novel.Chapter;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
	List<Chapter> findByNovelId(Long novelId);
}
