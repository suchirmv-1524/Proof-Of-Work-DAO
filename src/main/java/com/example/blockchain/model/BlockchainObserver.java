package com.example.blockchain.model;

/**
 * Observer interface for blockchain updates
 * This follows the Observer pattern where observers can subscribe to blockchain events
 */
public interface BlockchainObserver {
    void onBlockAdded(Block block);
    void onTransactionAdded(Transaction transaction);
} 