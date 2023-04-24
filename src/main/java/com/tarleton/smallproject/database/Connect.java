package com.tarleton.smallproject.database;

import com.tarleton.smallproject.Account;
import com.tarleton.smallproject.User;
import com.tarleton.smallproject.account_types.CheckingAccount;
import com.tarleton.smallproject.account_types.SavingAccount;

import java.sql.*;
import java.util.ArrayList;

public class Connect {

    public static Connection conn;

    public static void connect() {
        try {
            String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/src/main/resources/banksqlfile.db";
            conn = DriverManager.getConnection(url);

            System.out.println("Connected to database.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static User login(String username, String password) {
        String getUserString = "select * from users where username = ? and password = ?";
        try (PreparedStatement getUserStatement = conn.prepareStatement(getUserString)) {
            getUserStatement.setString(1, username);
            getUserStatement.setString(2, password);
            ResultSet userSet = getUserStatement.executeQuery();
            return new User(userSet.getInt("user_id"), userSet.getString("fName"),
                    userSet.getString("lName"),
                    getAccounts(userSet.getInt("user_id")));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static ArrayList<Account> getAccounts(int user_id) {
        String getAccountString = "select * from access where user_id = ?";
        ArrayList<Account> accounts = new ArrayList<>();

        try (PreparedStatement getAccountStatement = conn.prepareStatement(getAccountString)) {
            getAccountStatement.setInt(1, user_id);
            ResultSet accountsSet = getAccountStatement.executeQuery();

            while (accountsSet.next()) {
                accounts.add(getAccount(accountsSet.getInt("account_id")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return accounts;
    }

    public static Account getAccount(int account_id) {
        String getAccountString = "select * from accounts where account_id = ?";

        try (PreparedStatement getAccountStatement = conn.prepareStatement(getAccountString)) {
            getAccountStatement.setInt(1, account_id);
            ResultSet accountsSet = getAccountStatement.executeQuery();
            return new Account(getChecking(accountsSet.getInt("checking_id")),
                    getSaving(accountsSet.getInt("saving_id")),
                    accountsSet.getString("account_name"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static CheckingAccount getChecking(int checking_id) {
        String getCheckingString = "select * from checking where checking_id = ?";

        try (PreparedStatement getCheckingStatement = conn.prepareStatement(getCheckingString)) {
            getCheckingStatement.setInt(1, checking_id);
            ResultSet checkingSet = getCheckingStatement.executeQuery();
            return new CheckingAccount(checking_id, checkingSet.getDouble("balance"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static SavingAccount getSaving(int saving_id) {
        String getSavingString = "select * from saving where saving_id = ?";

        try (PreparedStatement getSavingStatement = conn.prepareStatement(getSavingString)) {
            getSavingStatement.setInt(1, saving_id);
            ResultSet savingSet = getSavingStatement.executeQuery();
            return new SavingAccount(saving_id, savingSet.getDouble("balance"), savingSet.getDouble("interest"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void updateChecking(int checking_id, double newBalance) {
        String updateBalanceString = "update checking set balance = " + newBalance + " where checking_id = " + checking_id;

        try (Statement updateBalanceStatement = conn.createStatement()) {
            updateBalanceStatement.executeQuery(updateBalanceString);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateSaving(int saving_id, double newBalance) {
        String updateBalanceString = "update saving set balance = " + newBalance + " where saving_id = " + saving_id;

        try (Statement updateBalanceStatement = conn.createStatement()) {
            updateBalanceStatement.executeQuery(updateBalanceString);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createUser(String username, String password, String fName, String lName) {
        String addUserString = "insert into users (fName, lName, username, password) values (?, ?, ?, ?)";

        try (PreparedStatement addUserStatement = conn.prepareStatement(addUserString)) {
            addUserStatement.setString(1, fName);
            addUserStatement.setString(2, lName);
            addUserStatement.setString(3, username);
            addUserStatement.setString(4, password);
            addUserStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createAccount(int user_id, String accountName) {
        int checkingID = createChecking();
        int savingID = createSaving();

        String addAccountString = "insert into accounts (checking_id, saving_id, account_name) values (" +
                checkingID + ", " + savingID + ", " + accountName + ")";

        try (PreparedStatement addAccountStatement = conn.prepareStatement(addAccountString, Statement.RETURN_GENERATED_KEYS)) {
            addAccountStatement.executeUpdate();
            addAccess(user_id, addAccountStatement.getGeneratedKeys().getInt(1));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addAccess(int user_id, int account_id) {
        String addAccessString = "insert into access (account_id, user_id) values (" + account_id + ", " + user_id + ")";

        try (Statement addAccessStatement = conn.createStatement()) {
            addAccessStatement.executeUpdate(addAccessString);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int createChecking() {
        String addCheckingString = "insert into checking (balance) values (0)";

        try (PreparedStatement addCheckingStatement = conn.prepareStatement(addCheckingString, Statement.RETURN_GENERATED_KEYS)) {
            addCheckingStatement.executeUpdate();

            return addCheckingStatement.getGeneratedKeys().getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public static int createSaving() {
        String addSavingString = "insert into saving (balance) values (0)";

        try (PreparedStatement addCheckingStatement = conn.prepareStatement(addSavingString, Statement.RETURN_GENERATED_KEYS)) {
            addCheckingStatement.executeUpdate();

            return addCheckingStatement.getGeneratedKeys().getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }
}
