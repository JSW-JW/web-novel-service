package com.example.webnovelservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webnovelservice.model.entity.transaction.OwnershipTokenTransaction;

@Repository
public interface OwnershipTokenTransactionRepository extends JpaRepository<OwnershipTokenTransaction, Long> {

	Optional<OwnershipTokenTransaction> findByUserId(Long userId);

}
