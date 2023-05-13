package com.example.webnovelservice.novel.domain.entity;

import java.util.Set;

import com.example.webnovelservice.commons.BaseTimeEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "showcase_type")
public class ShowcaseType extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String description;

	@ManyToMany(mappedBy = "showcaseTypes")
	private Set<Novel> novels;
}
