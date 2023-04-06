package com.example.webnovelservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webnovelservice.model.entity.transaction.NovelTokenCounter;

@Repository
public interface NovelTokenCounterRepository extends JpaRepository<NovelTokenCounter, Long> {
	Optional<NovelTokenCounter> findByUserIdAndNovelId(Long userId, Long novelId);
}
