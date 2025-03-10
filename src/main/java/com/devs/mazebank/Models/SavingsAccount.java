package com.devs.mazebank.Models;

import javafx.beans.property.DoubleProperty;

public class SavingsAccount extends Account{

    // The withdrawal Limit from the savings
     private final DoubleProperty withdrawalLimit;

    public SavingsAccount(String owner, String accountNumber, double balance, DoubleProperty wLimit) {
        super(owner, accountNumber, balance);
        this.withdrawalLimit = wLimit;
    }

    public DoubleProperty withdrawalLimitProperty() {
        return withdrawalLimit;
    }
}
