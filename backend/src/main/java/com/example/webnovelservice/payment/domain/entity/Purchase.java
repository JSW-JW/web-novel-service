package com.example.webnovelservice.payment.domain.entity;


import com.example.webnovelservice.commons.BaseTimeEntity;
import com.example.webnovelservice.chapter.domain.entity.Chapter;
import com.example.webnovelservice.user.domain.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "purchase")
@Getter
@Setter
public class Purchase extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private User user;

	@ManyToOne
	private Chapter chapter;
}
