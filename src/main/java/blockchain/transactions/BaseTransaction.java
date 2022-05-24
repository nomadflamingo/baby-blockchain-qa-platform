package blockchain.transactions;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

import accounts.Account;
import accounts.types.UserAccount;
import blockchain.Blockchain;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import cryptography.ECDSASignature;
import helpers.Json;
import helpers.ValidationResult;

public abstract class BaseTransaction implements Serializable {
    // A variable to tell how many fees required for given transaction type (depends on certain transaction type)
    protected static int minimumFees = 0;

    protected final TransactionType type;
    protected final String from;
    protected final String to;
    protected final int nonce;
    protected final int amount;
    protected final int commission;
    protected String id;

    /** Constructor with precomputed hash. Used to deserialize transactions received from other nodes */
    protected BaseTransaction(
            TransactionType type,
            String from,
            String to,
            int nonce,
            int amount,
            int commission,
            String id)
    {
        this.type = type;
        this.from = from;
        this.to = to;
        this.nonce = nonce;
        this.amount = amount;
        this.commission = commission;
        this.id = id;
    }

    /** Constructor without hash. Used to create transactions in the wallet */
    protected BaseTransaction(
            TransactionType type,
            String from,
            String to,
            int nonce,
            int amount,
            int commission)
    {
        this.type = type;
        this.from = from;
        this.to = to;
        this.nonce = nonce;
        this.amount = amount;
        this.commission = commission;

        this.id = hash();
    }


    public static int getMinimumFees() {
        return minimumFees;
    }

    public TransactionType getType() {
        return type;
    }

    public String getFrom()  {
        return from;
    }

    public String getTo()  {
        return to;
    }

    public int getNonce() {
        return nonce;
    }

    public int getAmount() {
        return amount;
    }

    public int getCommission() {
        return commission;
    }

    public String getId() {
        return id;
    }

    /**
     * <p>Base Validator for transactions. Used only in subclasses</p>
     * <ul>
     *     <li>
     *         Checks that signature is valid
     *     </li>
     *     <li>
     *         Checks that public key hash matches transaction from address
     *     </li>
     *     <li>
     *         Checks that nonce, amount and commission are not negative
     *     </li>
     *     <li>
     *         Checks that total transaction cost for recipient (amount + commission) doesn't overflow
     *     </li>
     *     <li>
     *         Checks that recipient has enough tokens to perform operation.
     *         (If recipient account not found, checks if total cost for recipient is 0)
     *     </li>
     *     <li>
     *         Checks that account nonce is the same as transaction nonce
     *         (If recipient account not found, checks if nonce is 0)
     *     </li>
     * </ul>
     * @return ValidationResult object
     */
    public ValidationResult validate(String txJson, byte[] signature, PublicKey publicKey) throws Exception {
        // Check signature
        if (!ECDSASignature.verifySignature(signature, txJson, publicKey)) {  // TODO: implement publicKey recovery
            return new ValidationResult(false, "Transaction signature is invalid");
        }

        // Check that public key hash matches from address
        if (!Account.getAddressFromPubKey(publicKey).equals(from)) {
            return new ValidationResult(false, "Public key is invalid for this transaction");
        }

        // Check that fields are not
        if (nonce < 0) return new ValidationResult(false, "Transaction nonce cannot be negative");
        if (amount < 0) return new ValidationResult(false, "Transaction amount cannot be negative");
        if (commission < 0) return new ValidationResult(false, "Transaction commission cannot be negative");

        // Use addExact to check for overflow
        int totalSum;
        try {
            totalSum = Math.addExact(amount, commission);
        } catch (ArithmeticException e) {
            return new ValidationResult(false, "Transaction spends too many tokens");
        }

        // Check that recipient has enough tokens
        Account recipient = Blockchain.getAccounts().get(from);

        // If recipient account is not yet in blockchain (new account) and transaction pays something, return false
        if (recipient == null) {
            if (totalSum > 0) {
                return new ValidationResult(false, "Recipient account not found");
            }
        } else {  // recipient account is not null
            if (recipient.getBalance() < totalSum) {
                return new ValidationResult(false, "Recipient account doesn't have enough tokens");
            }
            if (recipient.getNonce() > nonce) {
                return new ValidationResult(false, "Nonce is invalid");
            }
        }

        return new ValidationResult(true, "Transaction is valid");
    }

    /**
     * Base transaction executor code. Does the following:
     * <ul>
     *     <li>
     *         For new recipient accounts, creates them only if nonce is 0
     *     </li>
     *     <li>
     *         For existing recipient accounts, stops executing them if nonce is greater than their account's nonce
     *     </li>
     *     <li>
     *         Increments the account's nonce by 1
     *     </li>
     *     <li>
     *         Decrements recipient's balance by total transaction sum, which consists of amount + commission
     *     </li>
     *     <li>
     *         If no receiver account found, creates it
     *     </li>
     *     <li>
     *         Increments receiver's balance by total transaction sum
     *     </li>
     * </ul>
     */
    public abstract boolean execute() throws NoSuchAlgorithmException;

    protected abstract String hash();

    protected Hasher createBaseTxHasher() {
        HashFunction hashFunc = Hashing.sha256();

        Hasher hasher = hashFunc.newHasher()
                .putInt(type.ordinal())
                .putString(from, StandardCharsets.US_ASCII)
                .putString(to, StandardCharsets.US_ASCII)
                .putInt(nonce)
                .putInt(amount)
                .putInt(commission);

        return hasher;
    }

    @Override
    public String toString() {
        return Json.toJson(this);
    }

    public void print() {
        System.out.println(this);
    }

}
