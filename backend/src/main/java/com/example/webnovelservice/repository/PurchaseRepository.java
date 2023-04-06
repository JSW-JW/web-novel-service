package com.example.webnovelservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webnovelservice.model.entity.transaction.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
