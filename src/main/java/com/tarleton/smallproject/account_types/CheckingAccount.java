package com.tarleton.smallproject.account_types;

public class CheckingAccount extends MoneyAccount {

    public CheckingAccount(int id) {
        this.id = id;
    }

    public CheckingAccount(int id, double balance) {
        this(id);
        this.balance = balance;
    }
}
