package com.example.webnovelservice.payment.domain.entity;


import com.example.webnovelservice.commons.BaseTimeEntity;
import com.example.webnovelservice.novel.domain.entity.Novel;
import com.example.webnovelservice.user.domain.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

