package com.example.blockchain.service;

import com.example.blockchain.model.*;
import com.example.blockchain.repository.BlockRepository;
import com.example.blockchain.repository.TransactionRepository;
import com.example.blockchain.strategy.SimpleVerification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Principle: Dependency Inversion Principle (DIP)
 * - Depends on abstraction (VerificationStrategy) not concrete implementation.
 *
 * Principle: Single Responsibility Principle
 * - Only handles orchestration logic for blockchain operations.
 */
@Service
public class BlockchainService {

    private final Blockchain blockchain;
    private final BlockRepository blockRepository;
    private final TransactionRepository transactionRepository;
    private static final int INITIAL_DIFFICULTY = 2; // Number of leading zeros required

    @Autowired
    public BlockchainService(BlockRepository blockRepository, TransactionRepository transactionRepository) {
        this.blockchain = Blockchain.getInstance();
        this.blockchain.setVerificationStrategy(new SimpleVerification());
        this.blockRepository = blockRepository;
        this.transactionRepository = transactionRepository;
        
        // Load existing blocks from database
        loadBlocksFromDatabase();
    }

    private void loadBlocksFromDatabase() {
        List<BlockEntity> blockEntities = blockRepository.findAll();
        for (BlockEntity blockEntity : blockEntities) {
            List<Transaction> transactions = new ArrayList<>();
            for (TransactionEntity transactionEntity : blockEntity.getTransactions()) {
                Transaction transaction = TransactionFactory.createTransaction(
                    transactionEntity instanceof PaymentTransactionEntity ? "payment" : "smart",
                    transactionEntity.getSender(),
                    transactionEntity.getReceiver(),
                    transactionEntity.getAmount()
                );
                transactions.add(transaction);
            }
            blockchain.addBlock(new Block(transactions));
        }
    }

    @Transactional
    public void addTransaction(String sender, String receiver, double amount, String type) {
        // Create transaction entity
        TransactionEntity transactionEntity;
        if ("payment".equalsIgnoreCase(type)) {
            transactionEntity = new PaymentTransactionEntity(sender, receiver, amount);
        } else {
            transactionEntity = new SmartContractTransactionEntity(sender, receiver, amount);
        }

        // Get previous block's hash
        String previousHash = "0"; // Genesis block
        BlockEntity lastBlock = blockRepository.findTopByOrderByIdDesc();
        if (lastBlock != null) {
            previousHash = lastBlock.getHash();
        }

        // Create block entity
        BlockEntity blockEntity = new BlockEntity(System.currentTimeMillis(), previousHash, INITIAL_DIFFICULTY);
        blockEntity.getTransactions().add(transactionEntity);
        transactionEntity.setBlock(blockEntity);

        // Mine the block
        blockEntity.mineBlock();

        // Save to database
        blockRepository.save(blockEntity);
        transactionRepository.save(transactionEntity);

        // Add to in-memory blockchain
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(TransactionFactory.createTransaction(type, sender, receiver, amount));
        blockchain.addBlock(new Block(transactions));
    }

    public List<Block> getChain() {
        return blockchain.getChain();
    }

    /**
     * Get transaction statistics
     * @return Map of transaction type to count
     */
    public Map<String, Long> getTransactionStats() {
        return transactionRepository.findAll().stream()
            .collect(Collectors.groupingBy(
                tx -> tx instanceof PaymentTransactionEntity ? "Payment" : "Smart Contract",
                Collectors.counting()
            ));
    }

    /**
     * Get total transaction amount by type
     * @return Map of transaction type to total amount
     */
    public Map<String, Double> getTransactionAmounts() {
        return transactionRepository.findAll().stream()
            .collect(Collectors.groupingBy(
                tx -> tx instanceof PaymentTransactionEntity ? "Payment" : "Smart Contract",
                Collectors.summingDouble(TransactionEntity::getAmount)
            ));
    }

    /**
     * Get transactions by sender
     * @param sender Sender address
     * @return List of transactions
     */
    public List<TransactionEntity> getTransactionsBySender(String sender) {
        return transactionRepository.findBySender(sender);
    }

    /**
     * Get transactions by receiver
     * @param receiver Receiver address
     * @return List of transactions
     */
    public List<TransactionEntity> getTransactionsByReceiver(String receiver) {
        return transactionRepository.findByReceiver(receiver);
    }

    /**
     * Verify blockchain integrity
     * @return true if blockchain is valid
     */
    public boolean verifyBlockchain() {
        List<BlockEntity> blocks = blockRepository.findAll();
        if (blocks.isEmpty()) return true;

        // Verify genesis block
        BlockEntity firstBlock = blocks.get(0);
        if (!firstBlock.getPreviousHash().equals("0") || !firstBlock.isValid()) {
            return false;
        }

        // Verify each block
        for (int i = 1; i < blocks.size(); i++) {
            BlockEntity currentBlock = blocks.get(i);
            BlockEntity previousBlock = blocks.get(i - 1);

            // Check block hash
            if (!currentBlock.isValid()) {
                return false;
            }

            // Check previous hash link
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }

            // Verify all transactions in the block
            for (TransactionEntity tx : currentBlock.getTransactions()) {
                if (!tx.getHash().equals(tx.calculateHash())) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Get blockchain size
     * @return Number of blocks
     */
    public long getBlockchainSize() {
        return blockchain.getChain().size();
    }

    /**
     * Get total transaction count
     * @return Number of transactions
     */
    public long getTotalTransactionCount() {
        return transactionRepository.count();
    }

    /**
     * Gets the current mining difficulty
     * @return The difficulty level
     */
    public int getCurrentDifficulty() {
        return INITIAL_DIFFICULTY;
    }

    /**
     * Gets the total mining time for the last block
     * @return Mining time in milliseconds
     */
    public long getLastBlockMiningTime() {
        BlockEntity lastBlock = blockRepository.findTopByOrderByIdDesc();
        if (lastBlock == null) return 0;
        
        // Calculate mining time based on nonce
        // This is a simplified calculation
        return lastBlock.getNonce() * 10L; // Assuming 10ms per hash attempt
    }
}
