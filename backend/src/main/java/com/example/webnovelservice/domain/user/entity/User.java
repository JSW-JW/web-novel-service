package com.example.webnovelservice.domain.user.entity;
import java.util.Set;

import com.example.webnovelservice.domain.payment.entity.Purchase;
import com.example.webnovelservice.model.enums.AuthProvider;
import com.example.webnovelservice.domain.novel.entity.Novel;
import com.example.webnovelservice.domain.payment.entity.OwnershipTokenTransaction;
import com.example.webnovelservice.model.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Email
    @Column(nullable = false)
    private String email;

    private String imageUrl;

    @Column
    private Boolean emailVerified = false;

    @JsonIgnore
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @OneToMany(mappedBy = "author")
    private Set<Novel> novels;

    @OneToMany(mappedBy = "user")
    private Set<Favorite> favorites;

    @OneToMany(mappedBy = "user")
    private Set<Purchase> purchases;

    @OneToMany(mappedBy = "user")
    private Set<OwnershipTokenTransaction> ownershipTokenTransaction;
}
