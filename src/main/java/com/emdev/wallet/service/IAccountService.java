package com.emdev.wallet.service;

import com.emdev.wallet.exceptions.RequestException;
import com.emdev.wallet.model.Account;
import com.emdev.wallet.repository.AccountRepository;
import com.emdev.wallet.user.User;
import com.emdev.wallet.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IAccountService implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Account> getUserAccounts(Integer userId) {
        return userRepository.findAccountsByUser(userId);
    }

    @Override
    public Account getAccount(Integer accountId) {
        Optional<Account> account = accountRepository.findByAccountId(accountId);
        if (!account.isPresent()) {
            throw new RequestException("The requested account does not exist", "P-404",
                    HttpStatus.NOT_FOUND);
        }
        return account.get();

    }

    @Override
    public Account createAccount(Integer userId) {
        User user = userRepository.findById(userId).orElse(null);

        Account newAccount = new Account();
        user.getAccounts().add(newAccount);
        return accountRepository.save(newAccount);
    }

    @Override
    public void deleteAccount(Integer accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (!account.isPresent()) {
            throw new RequestException("The requested account does not exist", "P-404",
                    HttpStatus.NOT_FOUND);
        } else {
            accountRepository.deleteById(accountId);
        }
    }
}
