package com.example.blockchain.model;

import jakarta.persistence.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing a Block in the database
 * This class maps the Block model to a database table
 */
@Entity
@Table(name = "blocks")
public class BlockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private long timestamp;

    @Column(nullable = false, unique = true)
    private String hash;

    @Column(nullable = false)
    private String previousHash;

    @Column(nullable = false)
    private int nonce;

    @Column(nullable = false)
    private int difficulty; // Difficulty level for mining

    @OneToMany(mappedBy = "block", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionEntity> transactions = new ArrayList<>();

    // Default constructor required by JPA
    protected BlockEntity() {}

    public BlockEntity(long timestamp, String previousHash, int difficulty) {
        this.timestamp = timestamp;
        this.previousHash = previousHash;
        this.nonce = 0;
        this.difficulty = difficulty;
        this.hash = calculateHash();
    }

    /**
     * Calculates the SHA-256 hash of the block
     * Includes previous hash, timestamp, transactions, and nonce
     * @return The calculated hash
     */
    public String calculateHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            StringBuilder input = new StringBuilder();
            
            // Include all block data in hash calculation
            input.append(previousHash)
                 .append(timestamp)
                 .append(nonce);
            
            // Include all transaction hashes
            for (TransactionEntity tx : transactions) {
                input.append(tx.getHash());
            }
            
            byte[] hash = digest.digest(input.toString().getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Mines the block by finding a nonce that produces a hash with the required difficulty
     * Implements proof-of-work by requiring a certain number of leading zeros
     */
    public void mineBlock() {
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block mined! Hash: " + hash);
    }

    /**
     * Verifies if the block's hash is valid
     * Checks if the hash matches the calculated hash and meets difficulty requirements
     * @return true if the block is valid
     */
    public boolean isValid() {
        String target = new String(new char[difficulty]).replace('\0', '0');
        return hash.equals(calculateHash()) && 
               hash.substring(0, difficulty).equals(target);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public List<TransactionEntity> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionEntity> transactions) {
        this.transactions = transactions;
    }
} 