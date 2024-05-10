package com.emdev.wallet.service;

import com.emdev.wallet.model.Account;

import java.util.List;


public interface AccountService {

     List<Account> getUserAccounts(Integer userId);

     Account getAccount(Integer accountId);

     Account createAccount(Integer userId);

     void deleteAccount(Integer accountId);
}
