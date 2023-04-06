package com.example.webnovelservice.model.entity.favorite;


import com.example.webnovelservice.model.entity.BaseTimeEntity;
import com.example.webnovelservice.model.entity.novel.Novel;
import com.example.webnovelservice.model.entity.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "favorite")
public class Favorite extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private User user;

	@ManyToOne
	private Novel novel;
}
