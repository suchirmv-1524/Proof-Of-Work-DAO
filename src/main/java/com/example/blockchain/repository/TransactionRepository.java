package com.example.blockchain.repository;

import com.example.blockchain.model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for TransactionEntity
 * Provides CRUD operations and custom queries for transactions
 */
@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findBySender(String sender);
    List<TransactionEntity> findByReceiver(String receiver);
} 