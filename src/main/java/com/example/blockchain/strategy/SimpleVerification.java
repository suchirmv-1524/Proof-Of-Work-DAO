package com.example.blockchain.strategy;

import com.example.blockchain.model.Block;

public class SimpleVerification implements VerificationStrategy {
    public boolean verify(Block block) {
        return block != null && block.transactions != null && !block.transactions.isEmpty();
    }
}
