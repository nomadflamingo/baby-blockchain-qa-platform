package networking;

import blockchain.transactions.BaseTransaction;
import blockchain.transactions.TransactionType;
import blockchain.transactions.types.TransferCoinsTransaction;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Node {
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("> Enter port to expose:");
        String port = bufferedReader.readLine();

        // Enter port to expose
        // Enter ports to listen to


        // Command listener

    }
}
