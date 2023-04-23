package com.tarleton.smallproject;

import com.tarleton.smallproject.account_types.CheckingAccount;
import com.tarleton.smallproject.account_types.SavingAccount;

public record Account(CheckingAccount checkingAccount, SavingAccount savingAccount, String accountName) {
}
