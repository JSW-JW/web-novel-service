package com.example.webnovelservice.domain.novel;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.webnovelservice.domain.novel.entity.Chapter;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
	List<Chapter> findByNovelId(Long novelId);

	Integer countByNovelId(Long novelId);
}
