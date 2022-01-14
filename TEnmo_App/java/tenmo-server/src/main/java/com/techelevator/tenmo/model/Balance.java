package com.techelevator.tenmo.model;

import com.techelevator.tenmo.exceptions.InsufficientFunds;

import java.math.BigDecimal;

public class Balance {

    private BigDecimal balance = new BigDecimal(1000);

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void sendMoney(BigDecimal transferAmount) throws InsufficientFunds {

        BigDecimal newBalance = this.balance.subtract(transferAmount);
        if (newBalance.compareTo(transferAmount) >= 0) {
            this.balance = newBalance;
        } else {
            throw new InsufficientFunds();
        }
    }

    public void receiveMoney(BigDecimal transferAmount) {
        this.balance = balance.add(transferAmount);
    }

}
