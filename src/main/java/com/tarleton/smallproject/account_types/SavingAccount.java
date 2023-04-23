package com.tarleton.smallproject.account_types;

public class SavingAccount extends MoneyAccount {

    private double interestRate;

    public SavingAccount(int id) {
        this.id = id;
    }

    public SavingAccount(int id, Double balance, double interestRate) {
        this(id);
        this.balance = balance;
        this.interestRate = interestRate;
    }
}
