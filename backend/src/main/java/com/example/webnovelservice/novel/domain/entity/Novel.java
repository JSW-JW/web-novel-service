package com.example.webnovelservice.novel.domain.entity;

import java.util.Set;

import com.example.webnovelservice.chapter.domain.entity.Chapter;
import com.example.webnovelservice.commons.BaseTimeEntity;
import com.example.webnovelservice.user.domain.entity.Favorite;
import com.example.webnovelservice.user.domain.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "novel")
@Getter
@Setter
@ToString
public class Novel extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private Integer lastChapter;

	@Lob
	private String description;

	private String genre;

	private Long viewCount = 0L;

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
