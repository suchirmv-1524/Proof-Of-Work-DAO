package com.example.blockchain.model;

/**
 * Design Pattern: Factory Pattern
 * This class encapsulates the creation of various Transaction subtypes based on input parameters.
 * Promotes Open/Closed Principle - easy to add new types without modifying clients.
 */
public class TransactionFactory {
    public static Transaction createTransaction(String type, String sender, String receiver, double amount) {
        return switch (type.toLowerCase()) {
            case "payment" -> new PaymentTransaction(sender, receiver, amount);
            case "smart" -> new SmartContractTransaction(sender, receiver, amount);
            default -> throw new IllegalArgumentException("Unknown transaction type");
        };
    }
}
