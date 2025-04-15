# Blockchain-Based DAO Platform

## Project Overview
This project implements a sophisticated blockchain-based Decentralized Autonomous Organization (DAO) platform that simulates a permissioned blockchain network. The platform enables secure and transparent transactions, smart contract execution, and decentralized governance through a robust implementation of blockchain technology.

## Blockchain Type
The project simulates a **Proof of Work (PoW) Blockchain** with the following characteristics:
- Mining-based consensus mechanism
- Computational puzzle solving for block validation
- Difficulty adjustment for mining
- SHA-256 cryptographic hashing
- Competitive block creation process

## Core Components

### 1. Payments System
The payments system forms the foundation of the DAO's financial infrastructure:
- Secure peer-to-peer transactions
- Transaction validation and verification
- Digital asset management
- Transaction history tracking
- Balance management and updates

### 2. Smart Contracts
Smart contracts are the backbone of the DAO's automated governance:
- Programmable business logic
- Self-executing agreements
- Automated decision-making
- Transparent execution
- Immutable contract terms

### 3. DAO Integration
The integration of payments and smart contracts creates a complete DAO ecosystem:
- Decentralized governance
- Automated treasury management
- Voting and proposal systems
- Member management
- Resource allocation

## Features

### Major Features
1. **Blockchain Core**
   - Block creation and validation
   - Chain synchronization
   - Consensus mechanism
   - Cryptographic security using SHA-256 hashing

2. **Transaction Management**
   - Secure transaction processing
   - Digital signature verification
   - Transaction history tracking
   - Balance management

3. **Smart Contract Engine**
   - Contract deployment
   - Execution environment
   - State management
   - Event handling

4. **DAO Governance**
   - Proposal system
   - Voting mechanism
   - Member management
   - Treasury control

### Minor Features
1. **User Interface**
   - Transaction monitoring
   - Contract interaction
   - Voting interface
   - Balance viewing

2. **Security Features**
   - Authentication
   - Authorization
   - Encryption
   - Audit logging

3. **Administrative Tools**
   - Network monitoring
   - Member management
   - System configuration
   - Backup and recovery

4. **Data Management**
   - Transaction history tracking
   - Block validation
   - Chain synchronization
   - State persistence

## Design Patterns and Principles

### 1. Singleton Pattern
- **Implementation**: `BlockchainService` class with private constructor and static instance
- **Location**: `com.example.blockchain.service`
- **Purpose**: Ensures a single instance of the blockchain service across the application
- **Why**: 
  - Maintains consistency in blockchain state across the application
  - Prevents multiple instances from causing conflicts in block validation
  - Ensures synchronized access to the blockchain ledger
  - Provides a single source of truth for blockchain operations
- **How**: 
  - Private constructor prevents direct instantiation
  - Static getInstance() method provides controlled access
  - Thread-safe implementation for concurrent access
  - Lazy initialization for resource optimization

### 2. Observer Pattern
- **Implementation**: Event listeners and publishers in `BlockchainService`
- **Location**: `com.example.blockchain.service`
- **Purpose**: Notifies interested parties about blockchain events
- **Why**:
  - Enables loose coupling between blockchain operations and their observers
  - Facilitates real-time updates for blockchain state changes
  - Supports multiple subscribers for blockchain events
  - Allows for dynamic addition/removal of observers
- **How**:
  - Event publisher interface for blockchain events
  - Observer interface for event subscribers
  - Event queue for asynchronous event processing
  - Thread-safe event notification mechanism

### 3. Factory Pattern
- **Implementation**: `BlockFactory` and `TransactionFactory` classes
- **Location**: `com.example.blockchain.factory`
- **Purpose**: Creates blockchain components with consistent initialization
- **Why**:
  - Encapsulates complex object creation logic
  - Ensures consistent initialization of blockchain components
  - Provides flexibility in object creation
  - Centralizes validation logic during creation
- **How**:
  - Factory interfaces for block and transaction creation
  - Concrete factory implementations for specific types
  - Builder pattern integration for complex objects
  - Validation during object creation

### 4. Data Access Object (DAO) Pattern
- **Implementation**: JPA repositories (`BlockRepository`, `TransactionRepository`)
- **Location**: `com.example.blockchain.repository`
- **Purpose**: Abstracts data access layer
- **Why**:
  - Separates data access logic from business logic
  - Provides a clean API for database operations
  - Enables easy switching of persistence mechanisms
  - Centralizes data access patterns
- **How**:
  - Repository interfaces for each entity
  - JPA annotations for object-relational mapping
  - Custom query methods for specific operations
  - Transaction management integration

### 5. Builder Pattern
- **Implementation**: Used in `Block` and `Transaction` classes
- **Location**: `com.example.blockchain.model`
- **Purpose**: Constructs complex objects step by step
- **Why**:
  - Handles complex object construction
  - Provides immutable objects
  - Enables fluent interface for object creation
  - Validates object state during construction
- **How**:
  - Static inner Builder classes
  - Method chaining for fluent interface
  - Validation in build() method
  - Immutable object creation

### 6. Strategy Pattern
- **Implementation**: Mining strategies and validation strategies
- **Location**: `com.example.blockchain.strategy`
- **Purpose**: Encapsulates interchangeable algorithms
- **Why**:
  - Enables runtime selection of algorithms
  - Provides flexibility in mining and validation
  - Facilitates easy addition of new strategies
  - Decouples algorithm implementation from usage
- **How**:
  - Strategy interfaces for mining and validation
  - Concrete strategy implementations
  - Context classes for strategy execution
  - Strategy selection based on configuration

### 7. Chain of Responsibility Pattern
- **Implementation**: Block validation chain
- **Location**: `com.example.blockchain.chain`
- **Purpose**: Processes requests through a chain of handlers
- **Why**:
  - Decouples sender and receiver of requests
  - Enables dynamic chain configuration
  - Provides flexibility in validation process
  - Supports easy addition of new validation rules
- **How**:
  - Handler interface for validation steps
  - Concrete handler implementations
  - Chain builder for dynamic configuration
  - Successor pattern for chain traversal

## Database Structure

### MySQL Database: `blockchain_db`

#### Tables

1. **blocks**
   ```sql
   CREATE TABLE blocks (
       id BIGINT PRIMARY KEY AUTO_INCREMENT,
       hash VARCHAR(255) NOT NULL,
       previous_hash VARCHAR(255),
       timestamp BIGINT NOT NULL,
       nonce BIGINT NOT NULL,
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
   );
   ```

2. **transactions**
   ```sql
   CREATE TABLE transactions (
       id BIGINT PRIMARY KEY AUTO_INCREMENT,
       hash VARCHAR(255) NOT NULL,
       sender VARCHAR(255) NOT NULL,
       receiver VARCHAR(255) NOT NULL,
       amount DECIMAL(19,4) NOT NULL,
       timestamp BIGINT NOT NULL,
       block_id BIGINT,
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       FOREIGN KEY (block_id) REFERENCES blocks(id)
   );
   ```

3. **smart_contracts**
   ```sql
   CREATE TABLE smart_contracts (
       id BIGINT PRIMARY KEY AUTO_INCREMENT,
       address VARCHAR(255) NOT NULL,
       code TEXT NOT NULL,
       state TEXT,
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
   );
   ```

### Persistence Strategy
- JPA/Hibernate for object-relational mapping
- Automatic schema generation
- Transaction management
- Connection pooling
- Caching mechanisms

## Technical Stack
- Java 17
- Spring Boot 3.2.0
- MySQL 8.0
- JPA/Hibernate
- Maven
- Lombok
- JUnit 5

## Getting Started
1. Clone the repository
2. Configure MySQL database
3. Update application.properties with database credentials
4. Run the application using Maven
5. Access the API endpoints

## API Documentation
The API documentation is available at `/swagger-ui.html` when the application is running.

## License
This project is licensed under the MIT License - see the LICENSE file for details. 