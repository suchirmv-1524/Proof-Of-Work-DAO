package com.example.blockchain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Entity class representing a Payment Transaction in the database
 * This class extends TransactionEntity for payment-specific transactions
 */
@Entity
@DiscriminatorValue("PAYMENT")
public class PaymentTransactionEntity extends TransactionEntity {
    // Default constructor required by JPA
    protected PaymentTransactionEntity() {}

    public PaymentTransactionEntity(String sender, String receiver, double amount) {
        super(sender, receiver, amount);
    }
} 