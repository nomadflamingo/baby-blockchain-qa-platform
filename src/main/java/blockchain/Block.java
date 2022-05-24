package blockchain;

import blockchain.transactions.BaseTransaction;
import blockchain.transactions.TransactionEntry;
import blockchain.transactions.TransactionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.google.common.collect.ImmutableList;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import helpers.Json;
import helpers.ValidationResult;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;

public final class Block {
    private final String hash;
    private final String hashPrevBlock;
    private final ImmutableList<TransactionEntry> transactions;

    public static Block genesis() {
        return new Block(new ArrayList<>(), null);
    }

    /** Block constructor with hash (used to reconstruct block from json) */
    public Block(
            @JsonProperty ArrayList<TransactionEntry> transactions,
            @JsonProperty String hashPrevBlock,
            @JsonProperty String hash)
    {
        this.transactions = ImmutableList.copyOf(transactions);
        this.hashPrevBlock = hashPrevBlock;
        this.hash = hash;
    }

    /** Block constructor without hash (used by validators to generate new block from a set of transactions) */
    public Block(ArrayList<TransactionEntry> transactions, String hashPrevBlock) {
        this.transactions = ImmutableList.copyOf(transactions);
        this.hashPrevBlock = hashPrevBlock;

        this.hash = hash();
    }

    public String getHash() {
        return hash;
    }

    public String getHashPrevBlock() {
        return hashPrevBlock;
    }

    public ImmutableList<TransactionEntry> getTransactions() {
        return transactions;
    }

    public String toString() {
        return Json.toJson(this);
    }

    public void print() {
        System.out.println(this);
    }

    private String hash() {
        HashFunction hashFunc = Hashing.sha256();

        Hasher hasher = hashFunc.newHasher();

        hasher.putString(hashPrevBlock, StandardCharsets.US_ASCII);
        for (TransactionEntry tx : transactions) {
            hasher.putString(tx.transaction().getId(), StandardCharsets.UTF_8);
        }
        return hasher.hash().toString();
    }

    public ValidationResult validate() throws Exception {
        // Check that hash is valid
        if (!hash.equals(hash())) {
            return new ValidationResult(false, "Block hash is invalid");
        }

        // For every transaction in the block, check if it is valid, and if not, consider the entire block invalid
        for (TransactionEntry tx : transactions) {
            ValidationResult txValidationResult = tx.transaction().validate(tx.txJson(), tx.sig(), tx.publicKey());
            if (!txValidationResult.result()) return new ValidationResult(false, "Block contains invalid transactions");
        }

        return new ValidationResult(true, "Block is valid");
    }

    public void execute() {
        // TODO: implement
    }
}
