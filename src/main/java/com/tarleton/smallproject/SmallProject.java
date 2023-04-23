package com.tarleton.smallproject;

import com.tarleton.smallproject.account_types.MoneyAccount;
import com.tarleton.smallproject.database.Connect;

import java.util.Scanner;

public class SmallProject {
    private final static Scanner scanner = new Scanner(System.in);
    private static User user;

    public static void main(String[] args) {
        Connect.connect();

        mainTitleScreen();
        createOrSelectAccount();
    }

    public static void mainTitleScreen() {
        System.out.println("""
                1 - Login
                2 - Create Account
                3 - Exit
                Select an option:\s""");
        int input = scanner.nextInt();

        switch (input) {
            case 1 -> login();
            case 2 -> createUser();
            case 3 -> System.exit(0);
            default -> {
                System.out.println("Incorrect input please try again.");
                mainTitleScreen();
            }
        }
    }

    public static void login() {
        System.out.println("Please enter your username: ");
        String username = scanner.nextLine();
        System.out.println("Please enter your password: ");
        String password = scanner.nextLine();

        user = Connect.login(username, password);

        if (user != null) {
            System.out.println("Welcome " + user.getFirstName() + " " + user.getLastName() + "!");
            return;
        }

        System.out.println("Incorrect username or password entered.");
        System.out.println("""
                1 - Try again
                2 - Create account
                Select an option:\s""");
        int input = scanner.nextInt();

        switch (input) {
            case 1 -> login();
            case 2 -> createUser();
            default -> {
                System.out.println("Incorrect input please log in again.");
                login();
            }
        }
    }

    public static void createUser() {
        System.out.println("Enter username: ");
        String username = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();
        System.out.println("Enter first name: ");
        String fName = scanner.nextLine();
        System.out.println("Enter last name: ");
        String lName = scanner.nextLine();

        Connect.createUser(username, password, fName, lName);
        login();
    }

    public static void createOrSelectAccount() {
        System.out.println("""
                1 - Select Account
                2 - Create Account
                3 - Logout
                Enter an option:\s""");
        int input = scanner.nextInt();

        switch (input) {
            case 1 -> accountSelector();
            case 2 -> createAccount();
            case 3 -> logout();
            default -> {
                System.out.println("Incorrect input please try again.");
                createOrSelectAccount();
            }
        }
    }

    public static void accountSelector() {
        int counter = 1;
        for (Account account : user.getAccounts()) {
            System.out.println(counter + " - " + account.accountName());
            counter++;
        }
        System.out.println("Select an account: ");
        int accountNumber = scanner.nextInt();

        if (accountNumber > counter || accountNumber <= 0) {
            System.out.println("Incorrect option entered please try again.");
            accountSelector();
        }

        Account account = user.getAccounts().get(accountNumber - 1);
        moneyAccountSelector(account);
    }

    public static void createAccount() {
        System.out.println("Enter account name: ");
        String accountName = scanner.nextLine();
        Connect.createAccount(user.getId(), accountName);
        createOrSelectAccount();
    }

    public static void moneyAccountSelector(Account account) {
        System.out.println("""
                1 - Checking
                2 - Savings
                Enter an option:\s""");
        int input = scanner.nextInt();

        switch (input) {
            case 1 -> moneyAccountLoop(account.checkingAccount());
            case 2 -> moneyAccountLoop(account.savingAccount());
            default -> {
                System.out.println("Incorrect option entered please try again.");
                moneyAccountSelector(account);
            }
        }
    }

    public static void moneyAccountLoop(MoneyAccount moneyAccount) {
        System.out.println("""
                1 - Check balance
                2 - Deposit
                3 - Withdraw
                4 - Select different account
                Enter an option:\s""");
        int input = scanner.nextInt();

        switch (input) {
            case 1 -> System.out.println("Balance is: " + moneyAccount.getBalance());
            case 2 -> {
                System.out.println("Enter deposit amount: ");
                double depositAmount = scanner.nextDouble();
                moneyAccount.deposit(depositAmount);
            }
            case 3 -> {
                System.out.println("Enter withdraw amount: ");
                double withdrawAmount = scanner.nextDouble();
                moneyAccount.withdraw(withdrawAmount);
            }
            case 4 -> createOrSelectAccount();
            default -> {
                System.out.println("Incorrect option entered please try again.");
                moneyAccountLoop(moneyAccount);
            }
        }
    }

    public static void logout() {
        for (Account account : user.getAccounts()) {
            Connect.updateChecking(account.checkingAccount().getId(), account.checkingAccount().getBalance());
            Connect.updateSaving(account.savingAccount().getId(), account.savingAccount().getBalance());
        }

        System.out.println("Goodbye!");
        System.exit(0);
    }
}