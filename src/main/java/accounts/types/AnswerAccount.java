package accounts.types;

import accounts.Account;

import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public class AnswerAccount extends Account {
    private String body;
    private String[] attachments;
    private final String authorId;
    private int reputation;

    public AnswerAccount(PublicKey publicKey, String authorId, String body, String[] attachments) throws NoSuchAlgorithmException {
        super(publicKey);

        this.authorId = authorId;
        this.attachments = attachments;
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String[] getAttachments() {
        return attachments;
    }

    public void setAttachments(String[] attachments) {
        this.attachments = attachments;
    }

    public String getAuthorId() {
        return authorId;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }
}
