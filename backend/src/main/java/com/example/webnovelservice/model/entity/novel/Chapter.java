package com.example.webnovelservice.model.entity.novel;

import java.util.Set;

import com.example.webnovelservice.model.entity.BaseTimeEntity;
import com.example.webnovelservice.model.entity.transaction.Purchase;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

	// Chapter attributes (e.g. title, content)

	@ManyToOne
	private Novel novel;

	@OneToMany(mappedBy = "chapter")
	private Set<Purchase> purchases;
}
