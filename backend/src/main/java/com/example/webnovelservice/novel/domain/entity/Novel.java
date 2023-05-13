package com.example.webnovelservice.novel.domain.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.webnovelservice.chapter.domain.entity.Chapter;
import com.example.webnovelservice.commons.BaseTimeEntity;
import com.example.webnovelservice.user.domain.entity.Favorite;
import com.example.webnovelservice.user.domain.entity.User;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "novel")
@Getter
@Setter
public class Novel extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;

	private Integer chapterCount;
	@Lob
	private String description;

	private Long viewCount = 0L;

	@ManyToMany
	@JoinTable(
		name = "Novel_Genre",
		joinColumns = { @JoinColumn(name = "novel_id") },
		inverseJoinColumns = { @JoinColumn(name = "genre_id") }
	)
	private Set<Genre> genres = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	private User author;

	@OneToMany(mappedBy = "novel", fetch = FetchType.LAZY)
	private Set<Chapter> chapters;

	@OneToMany(mappedBy = "novel", fetch = FetchType.LAZY)
	private Set<Favorite> favorites;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "novel_showcase_type",
		joinColumns = @JoinColumn(name = "novel_id"),
		inverseJoinColumns = @JoinColumn(name = "showcase_type_id")
	)
	private Set<ShowcaseType> showcaseTypes;
}
