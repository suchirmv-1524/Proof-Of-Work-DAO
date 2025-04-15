package com.example.blockchain.strategy;

import com.example.blockchain.model.Block;

public interface VerificationStrategy {
    boolean verify(Block block);
}
