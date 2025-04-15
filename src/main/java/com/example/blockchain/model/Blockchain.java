package com.example.blockchain.model;

import com.example.blockchain.strategy.VerificationStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Design Patterns:
 * - Singleton Pattern: Ensures a single global instance of Blockchain.
 * - Strategy Pattern: Verification behavior can be changed at runtime.
 * - Observer Pattern: Notifies observers of blockchain updates.
 *
 * Principles:
 * - Single Responsibility: Manages only the chain and block validation.
 * - Open/Closed: Open for strategy extension without modifying this class.
 */
public class Blockchain {
    private static Blockchain instance;
    private final List<Block> chain;
    private VerificationStrategy strategy;
    private final List<BlockchainObserver> observers;

    private Blockchain() {
        this.chain = new ArrayList<>();
        this.observers = new CopyOnWriteArrayList<>(); // Thread-safe list for observers
    }

    public static Blockchain getInstance() {
        if (instance == null) instance = new Blockchain();
        return instance;
    }

    public void setVerificationStrategy(VerificationStrategy strategy) {
        this.strategy = strategy;
    }

    public VerificationStrategy getVerificationStrategy() {
        return strategy;
    }

    public void addObserver(BlockchainObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(BlockchainObserver observer) {
        observers.remove(observer);
    }

    private void notifyBlockAdded(Block block) {
        observers.forEach(observer -> observer.onBlockAdded(block));
    }

    private void notifyTransactionAdded(Transaction transaction) {
        observers.forEach(observer -> observer.onTransactionAdded(transaction));
    }

    public void addBlock(Block block) {
        if (strategy.verify(block)) {
            chain.add(block);
            notifyBlockAdded(block);
            block.transactions.forEach(this::notifyTransactionAdded);
        }
    }

    public List<Block> getChain() {
        return chain;
    }
}
