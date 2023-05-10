package com.example.webnovelservice.novel.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webnovelservice.novel.domain.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
