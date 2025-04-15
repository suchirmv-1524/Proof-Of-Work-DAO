package com.example.blockchain.model;

/**
 * Principle: Liskov Substitution Principle (LSP)
 * - Subclasses like PaymentTransaction can replace this base class without affecting functionality.
 *
 * Principle: Open/Closed Principle
 * - New types of transactions can be added via inheritance without modifying existing logic.
 */
public abstract class Transaction {
    protected String sender;
    protected String receiver;
    protected double amount;

    public Transaction(String sender, String receiver, double amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }

    public abstract String details();
}
