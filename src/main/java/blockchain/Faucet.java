package blockchain;

import accounts.Account;

public class Faucet {
    private int faucetCoins;

    public Faucet(int faucetCoins) {
        this.faucetCoins = faucetCoins;
    }

    public int getFaucetCoins() {
        return faucetCoins;
    }

    public void getTokensFromFaucet(Account account, int amount) {
        // Check that the amount is not greater than total coins in the faucet
        if (amount > faucetCoins) throw new IllegalArgumentException("Cannot get tokens from faucet: invalid amount");

        try {
            account.updateBalance(amount);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        // Update faucet
        faucetCoins -= amount;
    }
}
