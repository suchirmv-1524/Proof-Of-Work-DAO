package com.example.blockchain.model;

public class SmartContractTransaction extends Transaction {
    public SmartContractTransaction(String sender, String receiver, double amount) {
        super(sender, receiver, amount);
    }

    @Override
    public String details() {
        return "SmartContract executed from " + sender + " to " + receiver + " of " + amount;
    }
}
