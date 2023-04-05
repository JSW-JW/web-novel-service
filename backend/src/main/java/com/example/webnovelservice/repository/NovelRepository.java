package com.example.webnovelservice.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.webnovelservice.model.entity.novel.Novel;

@Repository
public interface NovelRepository extends JpaRepository<Novel, Long> {

	@Query("SELECT n FROM Novel n WHERE n.id IN (SELECT p.chapter.novel.id FROM Purchase p GROUP BY p.chapter.novel.id ORDER BY COUNT(p.chapter.novel.id) DESC)")
	List<Novel> findBestSeller();

	@Query("SELECT n FROM Novel n WHERE n.id IN (SELECT p.chapter.novel.id FROM Purchase p WHERE p.createdAt >= :date GROUP BY p.chapter.novel.id ORDER BY COUNT(p.chapter.novel.id) DESC)")
	List<Novel> findNewReleaseBestSeller(@Param("date") LocalDate date);

}
