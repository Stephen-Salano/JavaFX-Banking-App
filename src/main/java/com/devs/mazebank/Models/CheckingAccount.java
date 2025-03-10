package com.devs.mazebank.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CheckingAccount  extends Account{
    //number of transactions a client can do per day
    private final IntegerProperty transactionLimit;

    public CheckingAccount(String owner, String accountNumber, double balance, int tlimit){
        super(owner, accountNumber, balance);
        this.transactionLimit = new SimpleIntegerProperty(this, "Transaction Limit",tlimit );
    }

    public IntegerProperty transactionLimitProperty() {
        return transactionLimit;
    }
}
