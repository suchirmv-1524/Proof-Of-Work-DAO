package com.example.blockchain.model;

import java.util.List;

public class Block {
    public long timestamp;
    public List<Transaction> transactions;

    public Block(List<Transaction> transactions) {
        this.timestamp = System.currentTimeMillis();
        this.transactions = transactions;
    }
}
