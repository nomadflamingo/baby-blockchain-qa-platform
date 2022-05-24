package blockchain.transactions.types;

import accounts.Account;
import accounts.types.UserAccount;
import blockchain.Blockchain;
import blockchain.transactions.BaseTransaction;
import blockchain.transactions.TransactionType;
import helpers.ValidationResult;
import com.fasterxml.jackson.annotation.JsonProperty;
import helpers.Json;

import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Objects;

public final class TransferCoinsTransaction extends BaseTransaction {

    /** Constructor with hash. Used to deserialize transactions received from other nodes */
    public TransferCoinsTransaction(
        @JsonProperty("from") String from,
        @JsonProperty("to") String to,
        @JsonProperty("nonce") int nonce,
        @JsonProperty("amount") int amount,
        @JsonProperty("commission") int commission,
        @JsonProperty("id") String id)
    {
        super(TransactionType.TRANSFER_COINS, from, to, nonce, amount, commission, id);
    }

    /** Constructor without hash as an argument (used to create transaction internally inside wallet) */
    public TransferCoinsTransaction(
        String from,
        String to,
        int nonce,
        int amount,
        int commission)
    {
        super(TransactionType.TRANSFER_COINS, from, to, nonce, amount, commission);
    }

    @Override
    public ValidationResult validate(String txJson, byte[] signature, PublicKey publicKey) throws Exception {
        // Validate transaction using base transaction validator. If it returned false, return it
        ValidationResult baseResult = super.validate(txJson, signature, publicKey);
        if (!baseResult.result()) return baseResult;

        // Validate tx hash
        if (!Objects.equals(id, hash())) {
            return new ValidationResult(false, "Transaction hash is not valid");
        }

        // Get recipient and receiver
        Account recipient = Blockchain.getAccounts().get(from);
        Account receiver = Blockchain.getAccounts().get(to);

        // If recipient account not found, transaction is either incorrect or meaningless
        if (recipient == null) {
            return new ValidationResult(false, "Recipient account not found");
        }

        // Check that transaction pays to a user account or that receiver is not is accounts list yet
        if (receiver != null && !(receiver instanceof UserAccount)) {
            return new ValidationResult(false, "Receiver account type is not valid");
        }

        return new ValidationResult(true, "Transaction is valid");
    }

    @Override
    public boolean execute() throws NoSuchAlgorithmException {
        // Get recipient and receiver accounts
        Account recipient = Blockchain.getAccounts().get(from);  // Recipient is not null, if transaction is validated
        Account receiver = Blockchain.getAccounts().get(to);

        // Stop executing, if transaction nonce is greater than current recipient account nonce
        if (nonce > recipient.getNonce()) return false;

        // Increment the recipient's nonce
        recipient.incrementNonce();

        // Calculate total sum
        int totalSum = Math.addExact(amount, commission);

        // Set new recipient account balance
        recipient.updateBalance(recipient.getBalance() - totalSum);

        // If receiver is null, create new receiver account and add it to the blockchain
        if (receiver == null) {
            receiver = new UserAccount(to);
            Blockchain.addAccount(receiver);
        }

        // Set the new receiver account balance
        receiver.updateBalance(receiver.getBalance() + amount);

        return true;
    }

    @Override
    public String toString() {
        return Json.toJson(this);
    }

    @Override
    public void print() {
        System.out.println(this);
    }

    // Uses default transaction hasher to get id, because doesn't contain extra fields
    public String hash() {
        return createBaseTxHasher().hash().toString();
    }
}
