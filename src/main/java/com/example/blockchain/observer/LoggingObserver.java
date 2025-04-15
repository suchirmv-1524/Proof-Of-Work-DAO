package com.example.blockchain.observer;

import com.example.blockchain.model.Block;
import com.example.blockchain.model.BlockchainObserver;
import com.example.blockchain.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Concrete observer implementation that logs blockchain events
 * This demonstrates the Observer pattern in action
 */
public class LoggingObserver implements BlockchainObserver {
    private static final Logger logger = LoggerFactory.getLogger(LoggingObserver.class);

    @Override
    public void onBlockAdded(Block block) {
        logger.info("New block added at timestamp: {}", block.timestamp);
        logger.info("Block contains {} transactions", block.transactions.size());
    }

    @Override
    public void onTransactionAdded(Transaction transaction) {
        logger.info("New transaction added: {}", transaction.details());
    }
} 