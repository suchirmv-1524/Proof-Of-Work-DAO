package com.example.blockchain.repository;

import com.example.blockchain.model.BlockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for BlockEntity
 * Provides CRUD operations and custom queries for blocks
 */
@Repository
public interface BlockRepository extends JpaRepository<BlockEntity, Long> {
    BlockEntity findTopByOrderByIdDesc();
} 