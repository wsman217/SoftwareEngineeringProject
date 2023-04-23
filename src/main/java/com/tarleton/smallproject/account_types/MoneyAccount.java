package com.tarleton.smallproject.account_types;

import com.tarleton.smallproject.User;

import java.util.ArrayList;

public class MoneyAccount {

    public int getId() {
        return id;
    }

    protected int id;
    protected double balance;
    protected ArrayList<User> users = new ArrayList<>();

    public void withdraw(double amount) {
        this.balance -= amount;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public double getBalance() {
        return this.balance;
    }
}
