package com.example.blockchain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Entity class representing a Smart Contract Transaction in the database
 * This class extends TransactionEntity for smart contract-specific transactions
 */
@Entity
@DiscriminatorValue("SMART_CONTRACT")
public class SmartContractTransactionEntity extends TransactionEntity {
    // Default constructor required by JPA
    protected SmartContractTransactionEntity() {}

    public SmartContractTransactionEntity(String sender, String receiver, double amount) {
        super(sender, receiver, amount);
    }
} 