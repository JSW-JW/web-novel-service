package com.example.webnovelservice.chapter.domain.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.example.webnovelservice.commons.BaseTimeEntity;
import com.example.webnovelservice.novel.domain.entity.Novel;
import com.example.webnovelservice.payment.domain.entity.Purchase;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "chapter")
public class Chapter extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	@Column(name = "chapter_order")
	private Integer order;

	private String thumbnailUrl;

	private String contents;

	@NotNull
	private Integer tokensRequired;

	@ManyToOne(fetch = FetchType.LAZY)
	private Novel novel;

	@OneToMany(mappedBy = "chapter")
	private Set<Purchase> purchases;

}
