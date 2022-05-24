package accounts.types;

import accounts.Account;

import java.security.PublicKey;

public class UserAccount extends Account {
    private int reputation;

    public UserAccount(PublicKey publicKey) {
        super(publicKey);
        this.reputation = 0;
    }

    public UserAccount(String address) {
        super(address);
        this.reputation = 0;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }
}
