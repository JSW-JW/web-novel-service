package com.example.webnovelservice.payment.domain.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.webnovelservice.commons.BaseTimeEntity;
import com.example.webnovelservice.novel.domain.entity.Novel;
import com.example.webnovelservice.user.domain.entity.User;

import lombok.Data;

@Entity
@Data
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

