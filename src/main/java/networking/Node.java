package networking;

import blockchain.transactions.BaseTransaction;
import blockchain.transactions.TransactionType;
import blockchain.transactions.types.TransferCoinsTransaction;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Node {
    public static void main(String[] args) throws Exception {
        final ObjectMapper mapper = new ObjectMapper();

        final TransferCoinsTransaction tx = new TransferCoinsTransaction(
                "lalalala",
                "lulululu",
                0,
                12,
                2);

        final String json = mapper.writeValueAsString(tx);

        final JsonNode jsonNode = mapper.readTree(json);

        final TransactionType type = mapper.convertValue(jsonNode.get("type"), TransactionType.class);

        final Class<? extends BaseTransaction> cls;
        switch (type) {
            case TRANSFER_COINS -> {
                cls = TransferCoinsTransaction.class;
            }
            default -> throw new Exception();
        }

        final BaseTransaction txDeserialized = mapper.readValue(json, cls);
        System.out.println(txDeserialized);
    }
}
