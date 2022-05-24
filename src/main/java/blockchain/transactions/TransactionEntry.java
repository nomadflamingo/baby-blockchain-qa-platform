package blockchain.transactions;

import java.security.PublicKey;

/**
 * Transaction entry that is transmitted across peers and stored in blockchain
 */
public record TransactionEntry(BaseTransaction transaction, byte[] sig, String txJson, PublicKey publicKey) {}
