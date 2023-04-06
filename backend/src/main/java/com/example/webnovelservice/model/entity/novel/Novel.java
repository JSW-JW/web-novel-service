package com.example.webnovelservice.model.entity.novel;

import java.util.Set;

import com.example.webnovelservice.model.entity.BaseTimeEntity;
import com.example.webnovelservice.model.entity.favorite.Favorite;
import com.example.webnovelservice.model.entity.user.User;

import jakarta.persistence.Entity;
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

@Entity
@Table(name = "novel")
@Getter
@Setter
public class Novel extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private Integer lastChapter;

	@Lob
	private String description;

	private String genre;

	@ManyToOne
	private User author;

	@OneToMany(mappedBy = "novel")
	private Set<Chapter> chapters;

	@OneToMany(mappedBy = "novel")
	private Set<Favorite> favorites;

	@ManyToMany
	@JoinTable(
		name = "novel_showcase_type",
		joinColumns = @JoinColumn(name = "novel_id"),
		inverseJoinColumns = @JoinColumn(name = "showcase_type_id")
	)
	private Set<ShowcaseType> showcaseTypes;
}
