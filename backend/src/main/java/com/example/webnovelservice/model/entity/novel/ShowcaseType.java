package com.example.webnovelservice.model.entity.novel;

import java.util.Set;

import com.example.webnovelservice.model.entity.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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

	private String listType;

	private String listName;

	private String listDescription;

	@ManyToMany(mappedBy = "showcaseType")
	private Set<Novel> novels;
}
