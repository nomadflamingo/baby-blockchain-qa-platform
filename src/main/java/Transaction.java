import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public final class Transaction {
    private final Operation op;
    private final String from;
    private final String to;
    private final int nonce;
    private final int amount;
    private final int commission;
    private final ImmutableList<String> data;

    private final String txID;

    public Transaction(Operation op, String from, String to, int nonce, int amount, int commission, ImmutableList<String> data) {
        this.op = op;
        this.from = from;
        this.to = to;
        this.nonce = nonce;
        this.amount = amount;
        this.commission = commission;
        this.data = data;

        this.txID = genHashString();
    }

    public Operation getOp() {
        return op;
    }

    public String getFrom()  {
        return from;
    }

    public String getTo()  {
        return to;
    }

    public int getNonce() {
        return nonce;
    }

    public int getAmount() {
        return amount;
    }

    public int getCommission() {
        return commission;
    }

    public ImmutableList<String> getData() {
        return data;
    }

    public String getTxID() {
        return txID;
    }

    @Override
    public String toString() {

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public void print() {
        String jsonString = toString();
        System.out.println(jsonString);
    }

    private String genHashString() {
        HashFunction hashFunc = Hashing.sha256();

        return hashFunc.newHasher()
                .putInt(nonce)
                .putString(from, StandardCharsets.UTF_8)
                .putString(to, StandardCharsets.UTF_8)
                .putInt(amount)
                //.putObject(op, )  TO-DO: add hasher to the operation class
                .hash().toString();
    }
}
