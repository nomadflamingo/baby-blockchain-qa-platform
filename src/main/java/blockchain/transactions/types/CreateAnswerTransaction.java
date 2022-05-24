package blockchain.transactions.types;

import accounts.Account;
import accounts.types.AnswerAccount;
import accounts.types.QuestionAccount;
import accounts.types.UserAccount;
import blockchain.Blockchain;
import blockchain.transactions.BaseTransaction;
import blockchain.transactions.TransactionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import helpers.ValidationResult;

import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public class CreateAnswerTransaction extends BaseTransaction {
    private String answerBody;
    private String[] attachments;
    private String questionId;


    public CreateAnswerTransaction(
        @JsonProperty("type") TransactionType type,
        @JsonProperty("from") String from,
        @JsonProperty("to") String to,
        @JsonProperty("nonce") int nonce,
        @JsonProperty("amount") int amount,
        @JsonProperty("commission") int commission,
        @JsonProperty("answerBody") String answerBody,
        @JsonProperty("attachments") String[] attachments,
        @JsonProperty("questionId") String questionId,
        @JsonProperty("id") String id)
    {
        super(type, from, to, nonce, amount, commission, id);

        this.answerBody = answerBody;
        this.attachments = attachments;
        this.questionId = questionId;
    }

    public CreateAnswerTransaction(
        TransactionType type,
        String from,
        String to,
        int nonce,
        int amount,
        int commission,
        String answerBody,
        String[] attachments,
        String questionId)
    {
        super(type, from, to, nonce, amount, commission);

        this.answerBody = answerBody;
        this.attachments = attachments;
        this.questionId = questionId;
    }

    public String getAnswerBody() {
        return answerBody;
    }

    public String[] getAttachments() {
        return attachments;
    }

    public String getQuestionId() {
        return questionId;
    }

    @Override
    public ValidationResult validate(String txJson, byte[] signature, PublicKey publicKey) throws Exception {
        // Validate transaction using base transaction validator. If it returned false, return it
        ValidationResult baseResult = super.validate(txJson, signature, publicKey);
        if (!baseResult.result()) return baseResult;

        // Get recipient, receiver and question accounts
        Account recipient = Blockchain.getAccounts().get(from);
        Account receiver = Blockchain.getAccounts().get(to);
        Account question = Blockchain.getAccounts().get(questionId);

        // Check that the recipient account is of type user
        if (!(recipient instanceof UserAccount)) return new ValidationResult(false, "Recipient address not found");

        // Check that answer doesn't already exist
        if (Blockchain.getAccounts().containsKey(receiver.getAddress())) {
            return new ValidationResult(false, "Receiver account already exist");
        }

        // Check that question address is a valid address (exists in blockchain)
        if (!(question instanceof QuestionAccount))
            return new ValidationResult(false, "questionAddress is not of type Question");

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

        // Create answer
        Account answer = new AnswerAccount(from, to, answerBody, attachments, questionId);

        // Add answer to blockchain
        Blockchain.addAccount(answer);

        // Set question balance
        answer.updateBalance(amount);

        return true;
    }

    @Override
    protected String hash() {
        return null;
    }
}
