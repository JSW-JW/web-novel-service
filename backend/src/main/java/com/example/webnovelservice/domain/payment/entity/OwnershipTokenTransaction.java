package com.example.webnovelservice.domain.payment.entity;


import com.example.webnovelservice.domain.common.BaseTimeEntity;
import com.example.webnovelservice.domain.novel.entity.Novel;
import com.example.webnovelservice.domain.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ownership_token_transaction")
public class OwnershipTokenTransaction extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private User user;

	private Integer price;

	@OneToOne
	private Novel novel;

	private int tokensPurchased;
}

