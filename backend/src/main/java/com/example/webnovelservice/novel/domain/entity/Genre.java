package com.example.webnovelservice.novel.domain.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Genre {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String genre_name;

	@ManyToMany(mappedBy = "genres")
	private Set<Novel> novels = new HashSet<>();
}
