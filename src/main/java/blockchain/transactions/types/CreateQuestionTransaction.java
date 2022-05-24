package blockchain.transactions.types;

import accounts.Account;
import accounts.types.QuestionAccount;
import accounts.types.UserAccount;
import blockchain.Blockchain;
import blockchain.transactions.BaseTransaction;
import blockchain.transactions.TransactionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.hash.Hasher;
import helpers.ValidationResult;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public final class CreateQuestionTransaction extends BaseTransaction {
    private final String questionTitle;
    private final String questionBody;
    private final String[] attachments;
    private final long endTime;

    // Set minimum fees for this transaction type
    static {
        minimumFees = 2;
    }

    public CreateQuestionTransaction(
            @JsonProperty("from") String from,
            @JsonProperty("to") String to,
            @JsonProperty("nonce") int nonce,
            @JsonProperty("amount") int amount,
            @JsonProperty("commission") int commission,
            @JsonProperty("questionTitle") String questionTitle,
            @JsonProperty("questionBody") String questionBody,
            @JsonProperty("attachments") String[] attachments,
            @JsonProperty("endTime") long endTime,
            @JsonProperty("id") String id)
    {
        super(TransactionType.CREATE_QUESTION, from, to, nonce, amount, commission, id);

        this.questionTitle = questionTitle;
        this.questionBody = questionBody;
        this.attachments = attachments;
        this.endTime = endTime;
    }

    public CreateQuestionTransaction(
            String from,
            String to,
            int nonce,
            int amount,
            int commission,
            String questionTitle,
            String questionBody,
            String[] attachments,
            long endTime)
    {
        super(TransactionType.CREATE_QUESTION, from, to, nonce, amount, commission);

        this.questionTitle = questionTitle;
        this.questionBody = questionBody;
        this.attachments = attachments;
        this.endTime = endTime;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public String getQuestionBody() {
        return questionBody;
    }

    public String[] getAttachments() {
        return attachments;
    }

    public long getEndTime() {
        return endTime;
    }

    @Override
    public ValidationResult validate(String txJson, byte[] signature, PublicKey publicKey) throws Exception {
        // Validate transaction using base transaction validator. If it returned false, return it
        ValidationResult baseResult = super.validate(txJson, signature, publicKey);
        if (!baseResult.result()) return baseResult;

        // Check that 'from' is not null and 'from' is of type user
        if (!(Blockchain.getAccounts().get(from) instanceof UserAccount)) {
            return new ValidationResult(false, "'From' account is not of type user");
        }

        // Check that 'to' account is not in the accounts list yet
        if (Blockchain.getAccounts().containsKey(to)) {
            return new ValidationResult(false, "Question address is already registered in blockchain");
        }

        return new ValidationResult(true, "Transaction is valid");
    }

    @Override
    public boolean execute() throws NoSuchAlgorithmException {
        // Get recipient and receiver accounts
        Account recipient = Blockchain.getAccounts().get(from);  // Recipient is not null, if transaction is validated

        // Stop executing, if transaction nonce is greater than current recipient account nonce
        if (nonce > recipient.getNonce()) return false;

        // Increment the recipient's nonce
        recipient.incrementNonce();

        // Calculate total sum
        int totalSum = Math.addExact(amount, commission);

        // Create question
        Account question = new QuestionAccount(from, to, questionTitle, questionBody, attachments, endTime);

        // Set question balance
        question.updateBalance(amount);
        return true;
    }

    @Override
    public String hash() {
        Hasher hasher = createBaseTxHasher();

        hasher.putString(questionTitle, StandardCharsets.UTF_8);
        hasher.putString(questionBody, StandardCharsets.UTF_8);

        for (String attachment : attachments)
            hasher.putString(attachment, StandardCharsets.UTF_8);

        return hasher.hash().toString();
    }
}
