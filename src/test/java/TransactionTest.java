import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    void twoTransactions_withSameArguments_shouldHaveSameID() {
        Transaction tx1 = new Transaction(
                TransactionType.CREATE_QUESTION,
                "5hfsasdfgjasdjasdf",
                "fasdqhjq218d9sshg392hfg",
                2,
                100,
                1,
                ImmutableList.of("a", "b")
        );

        Transaction tx2 = new Transaction(
                TransactionType.CREATE_QUESTION,
                "5hfsasdfgjasdjasdf",
                "fasdqhjq218d9sshg392hfg",
                2,
                100,
                1,
                ImmutableList.of("a", "b")
        );

        Assertions.assertEquals(tx1.getTxID(), tx2.getTxID());
    }

    @Test
    void twoTransactions_withDifferentTypes_shouldHaveDifferentID() {
        Transaction tx1 = new Transaction(
                TransactionType.CREATE_QUESTION,
                "5hfsasdfgjasdjasdf",
                "fasdqhjq218d9sshg392hfg",
                2,
                100,
                1,
                ImmutableList.of("a", "b")
        );

        Transaction tx2 = new Transaction(
                TransactionType.TRANSFER_COINS,
                "5hfsasdfgjasdjasdf",
                "fasdqhjq218d9sshg392hfg",
                2,
                100,
                1,
                ImmutableList.of("a", "b")
        );

        Assertions.assertNotEquals(tx1.getTxID(), tx2.getTxID());
    }

    @Test
    void twoTransactions_withDifferentFromAddresses_shouldHaveDifferentID() {
        Transaction tx1 = new Transaction(
                TransactionType.CREATE_QUESTION,
                "5hfsasdfgjasdjasdf",
                "fasdqhjq218d9sshg392hfg",
                2,
                100,
                1,
                ImmutableList.of("a", "b")
        );

        Transaction tx2 = new Transaction(
                TransactionType.CREATE_QUESTION,
                "5hfsasdfgjasdjasdfg",
                "fasdqhjq218d9sshg392hfg",
                2,
                100,
                1,
                ImmutableList.of("a", "b")
        );

        Assertions.assertNotEquals(tx1.getTxID(), tx2.getTxID());
    }

    @Test
    void twoTransactions_withDifferentToAddresses_shouldHaveDifferentID() {
        Transaction tx1 = new Transaction(
                TransactionType.CREATE_QUESTION,
                "5hfsasdfgjasdjasdf",
                "fasdqhjq218d9sshg392hfg",
                2,
                100,
                1,
                ImmutableList.of("a", "b")
        );

        Transaction tx2 = new Transaction(
                TransactionType.CREATE_QUESTION,
                "5hfsasdfgjasdjasdf",
                "fasdqhjq218d9sshg392hfghdafs",
                2,
                100,
                1,
                ImmutableList.of("a", "b")
        );

        Assertions.assertNotEquals(tx1.getTxID(), tx2.getTxID());
    }

    @Test
    void twoTransactions_withDifferentNonces_shouldHaveDifferentID() {
        Transaction tx1 = new Transaction(
                TransactionType.CREATE_QUESTION,
                "5hfsasdfgjasdjasdf",
                "fasdqhjq218d9sshg392hfg",
                2,
                100,
                1,
                ImmutableList.of("a", "b")
        );

        Transaction tx2 = new Transaction(
                TransactionType.CREATE_QUESTION,
                "5hfsasdfgjasdjasdf",
                "fasdqhjq218d9sshg392hfg",
                3,
                100,
                1,
                ImmutableList.of("a", "b")
        );

        Assertions.assertNotEquals(tx1.getTxID(), tx2.getTxID());
    }

    @Test
    void twoTransactions_withDifferentAmount_shouldHaveDifferentID() {
        Transaction tx1 = new Transaction(
                TransactionType.CREATE_QUESTION,
                "5hfsasdfgjasdjasdf",
                "fasdqhjq218d9sshg392hfg",
                2,
                100,
                1,
                ImmutableList.of("a", "b")
        );

        Transaction tx2 = new Transaction(
                TransactionType.CREATE_QUESTION,
                "5hfsasdfgjasdjasdf",
                "fasdqhjq218d9sshg392hfg",
                2,
                200,
                1,
                ImmutableList.of("a", "b")
        );

        Assertions.assertNotEquals(tx1.getTxID(), tx2.getTxID());
    }

    @Test
    void twoTransactions_withDifferentCommission_shouldHaveDifferentID() {
        Transaction tx1 = new Transaction(
                TransactionType.CREATE_QUESTION,
                "5hfsasdfgjasdjasdf",
                "fasdqhjq218d9sshg392hfg",
                2,
                100,
                1,
                ImmutableList.of("a", "b")
        );

        Transaction tx2 = new Transaction(
                TransactionType.CREATE_QUESTION,
                "5hfsasdfgjasdjasdf",
                "fasdqhjq218d9sshg392hfg",
                2,
                100,
                8,
                ImmutableList.of("a", "b")
        );

        Assertions.assertNotEquals(tx1.getTxID(), tx2.getTxID());
    }

    @Test
    void twoTransactions_withDifferentData_shouldHaveDifferentID() {
        Transaction tx1 = new Transaction(
                TransactionType.CREATE_QUESTION,
                "5hfsasdfgjasdjasdf",
                "fasdqhjq218d9sshg392hfg",
                2,
                100,
                1,
                ImmutableList.of("a", "b")
        );

        Transaction tx2 = new Transaction(
                TransactionType.CREATE_QUESTION,
                "5hfsasdfgjasdjasdf",
                "fasdqhjq218d9sshg392hfg",
                2,
                100,
                1,
                ImmutableList.of("a", "bc")
        );

        Assertions.assertNotEquals(tx1.getTxID(), tx2.getTxID());
    }

    @Test
    void testToString() {
    }
}