package com.example.webnovelservice.domain.payment;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webnovelservice.domain.payment.entity.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
	Optional<Purchase> findByUserIdAndChapterId(Long userId, Long chapterId);
}
