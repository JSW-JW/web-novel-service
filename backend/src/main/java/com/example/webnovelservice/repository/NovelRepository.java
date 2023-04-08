package com.example.webnovelservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webnovelservice.model.entity.novel.Novel;

@Repository
public interface NovelRepository extends JpaRepository<Novel, Long> {
}
