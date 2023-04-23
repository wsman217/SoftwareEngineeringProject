package com.tarleton.smallproject;

import java.util.ArrayList;

public class User {

    private final int id;
    private final String firstName, lastName;
    private final ArrayList<Account> accounts = new ArrayList<>();

    public User(int id, String firstName, String lastName, ArrayList<Account> accounts) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accounts.addAll(accounts);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public int getId() {
        return id;
    }
}
