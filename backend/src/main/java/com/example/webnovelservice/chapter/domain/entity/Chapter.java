package com.example.webnovelservice.chapter.domain.entity;

import java.util.Set;

import com.example.webnovelservice.commons.BaseTimeEntity;
import com.example.webnovelservice.novel.domain.entity.Novel;
import com.example.webnovelservice.payment.domain.entity.Purchase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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

	private String contents;

	@NotNull
	private Integer tokensRequired;

	@ManyToOne
	private Novel novel;

	@OneToMany(mappedBy = "chapter")
	private Set<Purchase> purchases;

}
