package com.example.webnovelservice.domain.novel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.webnovelservice.domain.novel.entity.Novel;

@Repository
public interface NovelRepository extends JpaRepository<Novel, Long> {
	@Query("SELECT n FROM Novel n JOIN n.showcaseTypes st WHERE (st.id IN :showcaseTypeIds)")
	List<Novel> findByShowcaseTypeIds(@Param("showcaseTypeIds") List<Long> showcaseTypeIds);

	@Query("SELECT n FROM Novel n JOIN n.showcaseTypes st")
	List<Novel> findAllInShowcase();
}