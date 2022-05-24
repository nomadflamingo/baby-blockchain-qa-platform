package accounts.types;

import accounts.Account;

import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.ArrayList;

public class QuestionAccount extends Account {
    private final String authorId;
    private String title;
    private String body;
    private String[] attachments;
    private long endTime;
    private int reputation;
    private final ArrayList<String> answerIds;

    public QuestionAccount(
        String authorId,
        String questionAddress,
        String title, String body,
        String[] attachments,
        long endTime) throws NoSuchAlgorithmException
    {
        super(questionAddress);

        this.authorId = authorId;
        this.title = title;
        this.body = body;
        this.attachments = attachments;
        this.endTime = endTime;
        this.reputation = 0;
        this.answerIds = new ArrayList<>();
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public ArrayList<String> getAnswerIds() {
        return this.answerIds;
    }
}
