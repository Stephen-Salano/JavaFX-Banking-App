package com.devs.mazebank.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class SavingsAccount extends Account{

    // The withdrawal Limit from the savings
     private final IntegerProperty withdrawalLimit;

    public SavingsAccount(String owner, String accountNumber, double balance, int wLimit) {
        super(owner, accountNumber, balance);
        this.withdrawalLimit = new SimpleIntegerProperty(this, "Withdrawal Limit", wLimit);
    }

    public IntegerProperty withdrawalLimitProperty() {
        return withdrawalLimit;
    }
}
