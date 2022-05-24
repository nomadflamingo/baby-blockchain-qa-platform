package blockchain.transactions.types;

import accounts.types.UserAccount;
import blockchain.Blockchain;
import blockchain.transactions.BaseTransaction;
import blockchain.transactions.TransactionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.hash.Hasher;
import helpers.ValidationResult;

import java.nio.charset.StandardCharsets;
import java.security.PublicKey;

public final class CreateQuestionTransaction extends BaseTransaction {
    private String questionTitle;
    private String questionBody;
    private String[] attachments;

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
            @JsonProperty("attachments") String[] attachments)
    {
        super(TransactionType.CREATE_QUESTION, from, to, nonce, amount, commission);

        this.questionTitle = questionTitle;
        this.questionBody = questionBody;
        this.attachments = attachments;
    }

    @Override
    public ValidationResult validate(String txJson, byte[] signature, PublicKey publicKey) throws Exception {
        // Validate transaction using base transaction validator. If it returned false, return it
        ValidationResult baseResult = super.validate(txJson, signature, publicKey);
        if (!baseResult.result()) return baseResult;

        // Check that 'from' account is of type user
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
    public boolean execute() {
        return true;
        // TODO: implement
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
