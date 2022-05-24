//import blockchain.transactions.Transaction;
//import blockchain.transactions.TransactionType;
//import blockchain.transactions.types.TransferCoinsTransaction;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//class TransactionTest {
//
//    @Test
//    void twoTransactions_withSameArguments_shouldHaveSameID() {
//        Transaction tx1 = new TransferCoinsTransaction(
//                TransactionType.CREATE_QUESTION,
//                "5hfsasdfgjasdjasdf",
//                "fasdqhjq218d9sshg392hfg",
//                2,
//                100,
//                1,
//                new ArrayList<>(List.of("a", "b"))
//        );
//
//        Transaction tx2 = new Transaction(
//                Transaction.Type.CREATE_QUESTION,
//                "5hfsasdfgjasdjasdf",
//                "fasdqhjq218d9sshg392hfg",
//                2,
//                100,
//                1,
//                new ArrayList<>(List.of("a", "b"))
//        );
//
//        Assertions.assertEquals(tx1.getId(), tx2.getId());
//    }
//
//    @Test
//    void twoTransactions_withDifferentTypes_shouldHaveDifferentID() {
//        Transaction tx1 = new Transaction(
//                Transaction.Type.CREATE_QUESTION,
//                "5hfsasdfgjasdjasdf",
//                "fasdqhjq218d9sshg392hfg",
//                2,
//                100,
//                1,
//                new ArrayList<>(List.of("a", "b"))
//        );
//
//        Transaction tx2 = new Transaction(
//                Transaction.Type.TRANSFER_COINS,
//                "5hfsasdfgjasdjasdf",
//                "fasdqhjq218d9sshg392hfg",
//                2,
//                100,
//                1,
//                new ArrayList<>(List.of("a", "b"))
//        );
//
//        Assertions.assertNotEquals(tx1.getId(), tx2.getId());
//    }
//
//    @Test
//    void twoTransactions_withDifferentFromAddresses_shouldHaveDifferentID() {
//        Transaction tx1 = new Transaction(
//                Transaction.Type.CREATE_QUESTION,
//                "5hfsasdfgjasdjasdf",
//                "fasdqhjq218d9sshg392hfg",
//                2,
//                100,
//                1,
//                new ArrayList<>(List.of("a", "b"))
//        );
//
//        Transaction tx2 = new Transaction(
//                Transaction.Type.CREATE_QUESTION,
//                "5hfsasdfgjasdjasdfg",
//                "fasdqhjq218d9sshg392hfg",
//                2,
//                100,
//                1,
//                new ArrayList<>(List.of("a", "b"))
//        );
//
//        Assertions.assertNotEquals(tx1.getId(), tx2.getId());
//    }
//
//    @Test
//    void twoTransactions_withDifferentToAddresses_shouldHaveDifferentID() {
//        Transaction tx1 = new Transaction(
//                Transaction.Type.CREATE_QUESTION,
//                "5hfsasdfgjasdjasdf",
//                "fasdqhjq218d9sshg392hfg",
//                2,
//                100,
//                1,
//                new ArrayList<>(List.of("a", "b"))
//        );
//
//        Transaction tx2 = new Transaction(
//                Transaction.Type.CREATE_QUESTION,
//                "5hfsasdfgjasdjasdf",
//                "fasdqhjq218d9sshg392hfghdafs",
//                2,
//                100,
//                1,
//                new ArrayList<>(List.of("a", "b"))
//        );
//
//        Assertions.assertNotEquals(tx1.getId(), tx2.getId());
//    }
//
//    @Test
//    void twoTransactions_withDifferentNonces_shouldHaveDifferentID() {
//        Transaction tx1 = new Transaction(
//                Transaction.Type.CREATE_QUESTION,
//                "5hfsasdfgjasdjasdf",
//                "fasdqhjq218d9sshg392hfg",
//                2,
//                100,
//                1,
//                new ArrayList<>(List.of("a", "b"))
//        );
//
//        Transaction tx2 = new Transaction(
//                Transaction.Type.CREATE_QUESTION,
//                "5hfsasdfgjasdjasdf",
//                "fasdqhjq218d9sshg392hfg",
//                3,
//                100,
//                1,
//                new ArrayList<>(List.of("a", "b"))
//        );
//
//        Assertions.assertNotEquals(tx1.getId(), tx2.getId());
//    }
//
//    @Test
//    void twoTransactions_withDifferentAmount_shouldHaveDifferentID() {
//        Transaction tx1 = new Transaction(
//                Transaction.Type.CREATE_QUESTION,
//                "5hfsasdfgjasdjasdf",
//                "fasdqhjq218d9sshg392hfg",
//                2,
//                100,
//                1,
//                new ArrayList<>(List.of("a", "b"))
//        );
//
//        Transaction tx2 = new Transaction(
//                Transaction.Type.CREATE_QUESTION,
//                "5hfsasdfgjasdjasdf",
//                "fasdqhjq218d9sshg392hfg",
//                2,
//                200,
//                1,
//                new ArrayList<>(List.of("a", "b"))
//        );
//
//        Assertions.assertNotEquals(tx1.getId(), tx2.getId());
//    }
//
//    @Test
//    void twoTransactions_withDifferentCommission_shouldHaveDifferentID() {
//        Transaction tx1 = new Transaction(
//                Transaction.Type.CREATE_QUESTION,
//                "5hfsasdfgjasdjasdf",
//                "fasdqhjq218d9sshg392hfg",
//                2,
//                100,
//                1,
//                new ArrayList<>(List.of("a", "b"))
//        );
//
//        Transaction tx2 = new Transaction(
//                Transaction.Type.CREATE_QUESTION,
//                "5hfsasdfgjasdjasdf",
//                "fasdqhjq218d9sshg392hfg",
//                2,
//                100,
//                8,
//                new ArrayList<>(List.of("a", "b"))
//        );
//
//        Assertions.assertNotEquals(tx1.getId(), tx2.getId());
//    }
//
//    @Test
//    void twoTransactions_withDifferentData_shouldHaveDifferentID() {
//        Transaction tx1 = new Transaction(
//                Transaction.Type.CREATE_QUESTION,
//                "5hfsasdfgjasdjasdf",
//                "fasdqhjq218d9sshg392hfg",
//                2,
//                100,
//                1,
//                new ArrayList<>(List.of("a", "b"))
//        );
//
//        Transaction tx2 = new Transaction(
//                Transaction.Type.CREATE_QUESTION,
//                "5hfsasdfgjasdjasdf",
//                "fasdqhjq218d9sshg392hfg",
//                2,
//                100,
//                1,
//                new ArrayList<>(List.of("a", "bc"))
//        );
//
//        Assertions.assertNotEquals(tx1.getId(), tx2.getId());
//    }
//
//    @Test
//    void testToString() {
//    }
//}