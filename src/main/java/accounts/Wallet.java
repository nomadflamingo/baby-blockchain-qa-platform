package accounts;

import blockchain.transactions.BaseTransaction;
import cryptography.ECDSASignature;

import java.security.KeyPair;

public class Wallet {
    private KeyPair pair;
    private Account account;

    public Wallet(KeyPair pair) {
        this.pair = pair;
    }

    // TODO: implement this shit
//    public static Transaction createTransferCoinsTx() {
//
//    }
//
//    public static Transaction createCreateQuestionTx() {
//
//    }
//
//    public static Transaction createCreateAnswerTx() {
//
//    }
//
//    public static Transaction createCloseQuestionTx() {
//
//    }
//
//    public static Transaction createUpvoteQuestionTx() {
//
//    }
//
//    public static Transaction createDownvoteQuestionTx() {
//
//    }
//
//    public static Transaction createUpvoteAnswerTx() {
//
//    }

    public byte[] sign(BaseTransaction tx) throws Exception {
        return ECDSASignature.sign(pair.getPrivate(), tx.toString());
    }

    public void send(BaseTransaction tx) {
        // TODO: implement p2p sending
    }
}
