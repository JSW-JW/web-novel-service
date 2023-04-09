package com.example.webnovelservice.domain.payment.entity;


import com.example.webnovelservice.domain.common.BaseTimeEntity;
import com.example.webnovelservice.domain.novel.entity.Chapter;
import com.example.webnovelservice.domain.user.entity.User;

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
