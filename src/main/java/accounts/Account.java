package accounts;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import helpers.Hex;

import java.security.*;

public abstract class Account {
    private final String address;
    private int nonce;
    private int balance;

    /** Account constructor from address. Used to register accounts in blockchain, after first transaction is set */
    protected Account(String address) {
        this.address = address;

        // Set account default balance and nonce
        this.balance = 0;
        this.nonce = 0;
    }

    /** Account constructor from address. Used to create  */
    protected Account(PublicKey publicKey) {
        this.address = getAddressFromPubKey(publicKey);

        // Set account default balance and nonce
        this.balance = 0;
        this.nonce = 0;
    }

    public static String getAddressFromPubKey(PublicKey publicKey) {
        // Create new HashFunction instance and hash public key
        HashFunction hashFunc = Hashing.sha256();
        byte[] pubKeyHash = hashFunc.newHasher().putBytes(publicKey.getEncoded()).hash().asBytes();

        // Return the first 20 characters of the public key hash
        return Hex.hexFromBytes(pubKeyHash, 0, 20);
    }

    public String getAddress() {
        return address;
    }

    public int getNonce() {
        return nonce;
    }

    public void incrementNonce() {
        nonce++;
    }

    public int getBalance() {
        return balance;
    }

    /**
     * Updates account balance to a specified amount.
     * Careful with this one
     */
    public void updateBalance(int amount) throws IllegalStateException {
        if (amount < 0) throw new IllegalStateException("Amount cannot be negative");

        balance = amount;
    }
}
