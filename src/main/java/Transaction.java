import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

public final class Transaction {
    private final TransactionType type;
    private final String from;
    private final String to;
    private final int nonce;
    private final int amount;
    private final int commission;
    private final ImmutableList<String> data;

    private final String txID;

    public Transaction(TransactionType type, String from, String to, int nonce, int amount, int commission, ImmutableList<String> data) {
        validateNotNull(type, "Transaction type cannot be null");
        validateNotNull(from, "Transaction from address cannot be null");
        validateNotNull(to, "Transaction to address cannot be null");
        if (nonce < 0) throw new IllegalArgumentException("Transaction nonce cannot be negative");
        if (amount < 0) throw new IllegalArgumentException("Transaction amount cannot be negative");
        if (commission < 0) throw new IllegalArgumentException("Transaction commission cannot be negative");

        this.type = type;
        this.from = from;
        this.to = to;
        this.nonce = nonce;
        this.amount = amount;
        this.commission = commission;
        this.data = data;

        this.txID = generateHashString();
    }

    public TransactionType getType() {
        return type;
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

    private void validateNotNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }

    private String generateHashString() {
        HashFunction hashFunc = Hashing.sha256();

        Hasher hasher = hashFunc.newHasher()
                .putInt(type.getValue())
                .putString(from, StandardCharsets.UTF_8)
                .putString(to, StandardCharsets.UTF_8)
                .putInt(nonce)
                .putInt(amount)
                .putInt(commission);

        if (data != null) {
            for (String row : data) {
                hasher.putString(row, StandardCharsets.UTF_8);
            }
        }

        return hasher.hash().toString();
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
}
