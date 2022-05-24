package accounts;

import accounts.types.AnswerAccount;
import accounts.types.QuestionAccount;
import accounts.types.UserAccount;
import blockchain.Blockchain;
import blockchain.transactions.BaseTransaction;
import blockchain.transactions.types.CreateAnswerTransaction;
import blockchain.transactions.types.CreateQuestionTransaction;
import blockchain.transactions.types.TransferCoinsTransaction;
import cryptography.ECDSASignature;

import java.security.KeyPair;
import java.time.Instant;
import java.util.ArrayList;

/**
 * A class that is used to create and sign transactions
 */
public final class Wallet {
    public record AccountOwnership<T extends Account>(T account, KeyPair keyPair) {}

    private final KeyPair pair;
    private final Account account;
    private final ArrayList<AccountOwnership<QuestionAccount>> questions = new ArrayList<>();
    private final ArrayList<AccountOwnership<AnswerAccount>> answers = new ArrayList<>();

    public Wallet(KeyPair pair) {
            this.pair = pair;
            account = new UserAccount(pair.getPublic());
            Blockchain.addAccount(account);
    }

    public TransferCoinsTransaction createTransferCoinsTx(
            String to,
            int amount,
            int commission)
    {
        return new TransferCoinsTransaction(account.getAddress(), to, account.getNonce(), amount, commission);
    }

    public CreateQuestionTransaction createCreateQuestionTx(
            int amount,
            int commission,
            String title,
            String body,
            String[] attachments,
            int durationInSeconds)
    {
        // Throw exception, if transaction fee is too low
        if (commission < CreateQuestionTransaction.getMinimumFees()) {
            throw new IllegalArgumentException(
                      "Transaction won't be accepted, "
                    + "because commission fee is too low for this transaction type. Should be at least "
                    + CreateQuestionTransaction.getMinimumFees());
        }

        // Get current UTC time
        long currentTime = Instant.now().getEpochSecond();

        // Calculate end time
        long endTime = currentTime + durationInSeconds;

        // Generate new key pair to have access to question
        KeyPair questionKeyPair = ECDSASignature.generateKeyPair();

        // Create new question account and add it both to wallet and to blockchain
        QuestionAccount question = new QuestionAccount(
                questionKeyPair.getPublic(),
                account.getAddress(),
                title,
                body,
                attachments,
                endTime);
        questions.add(new AccountOwnership<>(question, questionKeyPair));
        Blockchain.addAccount(question);

        return new CreateQuestionTransaction(
                account.getAddress(),
                question.getAddress(),
                account.getNonce(),
                amount,
                commission,
                title,
                body,
                attachments,
                endTime);
    }

    public CreateAnswerTransaction createCreateAnswerTx(
            int amount,
            int commission,
            String body,
            String[] attachments,
            String questionAddress)
    {
        // Throw exception, if transaction fee is too low
        if (commission < CreateAnswerTransaction.getMinimumFees()) {
            throw new IllegalArgumentException(
                    "Transaction won't be accepted, "
                            + "because commission fee is too low for this transaction type. Should be at least "
                            + CreateAnswerTransaction.getMinimumFees());
        }

        // Generate new key pair to have access to question
        KeyPair answerKeyPair = ECDSASignature.generateKeyPair();

        // Create new question account and add it both to wallet and to blockchain
        AnswerAccount answer = new AnswerAccount(
                answerKeyPair.getPublic(),
                account.getAddress(),
                body,
                attachments,
                questionAddress);
        answers.add(new AccountOwnership<>(answer, answerKeyPair));
        Blockchain.addAccount(answer);

        return new CreateAnswerTransaction(
                account.getAddress(),
                answer.getAddress(),
                account.getNonce(),
                amount,
                commission,
                body,
                attachments,
                questionAddress);
    }

    public byte[] sign(BaseTransaction tx) throws Exception {
        return ECDSASignature.sign(pair.getPrivate(), tx.toString());
    }
}
