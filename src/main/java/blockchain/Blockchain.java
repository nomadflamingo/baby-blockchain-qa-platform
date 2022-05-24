package blockchain;

import accounts.Account;
import blockchain.transactions.BaseTransaction;
import helpers.Json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public final class Blockchain {
    private static final ArrayList<Block> chain = new ArrayList<>();
    private static final HashMap<String, Account> accounts = new HashMap<>();
    private static final PriorityQueue<BaseTransaction> memoryPool = new PriorityQueue<>();

    // Once loaded in memory, add genesis block to chain
    static {
        chain.add(Block.genesis());
    }

    // Only one blockchain per node, so don't create instances
    private Blockchain() {}

    public static ArrayList<Block> getChain() {
        return chain;
    }

    public static HashMap<String, Account> getAccounts() {
        return accounts;
    }

    public static PriorityQueue<BaseTransaction> getMemoryPool() {
        return memoryPool;
    }

    public static void addBlock(Block validatedBlock) {
        chain.add(validatedBlock);
    }

    public static void addAccount(Account account) {
        accounts.put(account.getAddress(), account);
    }

    // TODO: test that prints correctly
    public static void printAccounts() {
        System.out.println(Json.toJson(accounts));
    }
}
