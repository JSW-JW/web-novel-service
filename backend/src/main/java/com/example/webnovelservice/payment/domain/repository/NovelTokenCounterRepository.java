package com.example.webnovelservice.payment.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webnovelservice.payment.domain.entity.NovelTokenCounter;

@Repository
public interface NovelTokenCounterRepository extends JpaRepository<NovelTokenCounter, Long> {
	Optional<NovelTokenCounter> findByUserIdAndNovelId(Long userId, Long novelId);
}
