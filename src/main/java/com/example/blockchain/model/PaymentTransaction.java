package com.example.blockchain.model;

public class PaymentTransaction extends Transaction {
    public PaymentTransaction(String sender, String receiver, double amount) {
        super(sender, receiver, amount);
    }

    @Override
    public String details() {
        return "Payment from " + sender + " to " + receiver + " of " + amount;
    }
}
