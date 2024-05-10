package com.emdev.wallet.service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import com.emdev.wallet.exceptions.RequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.emdev.wallet.model.Account;
import com.emdev.wallet.model.Deposit;
import com.emdev.wallet.model.Incomings;
import com.emdev.wallet.model.Movements;
import com.emdev.wallet.repository.AccountRepository;
import com.emdev.wallet.repository.DepositRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class IDepositService implements DepositService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private DepositRepository depositRepository;

    @Override
    public List<Deposit> getAccountDeposits(Integer accountId) {
        return accountRepository.findDepositsByAccount(accountId);
    }

    @Override
    public Deposit getDeposit(Integer depositId) {
        Optional<Deposit> deposit = depositRepository.findById(depositId);
        if (!deposit.isPresent()) {
            throw new RequestException("The requested deposit does not exist", "P-404",
                    HttpStatus.NOT_FOUND);
        }
        return deposit.get();
    }

    @Override
    public Deposit createDeposit(Integer accountId, Deposit deposit) throws ParseException {
        Optional<Account> account = accountRepository.findByAccountId(accountId);
        if (!account.isPresent()) {
            throw new RequestException("The requested account does not exist", "P-404",
                    HttpStatus.NOT_FOUND);
        }

        Deposit newDeposit = new Deposit(deposit.getAmount(), deposit.getDescription());
        Incomings newIncoming = new Incomings(newDeposit.getDate(), newDeposit.getAmount(), account.get().getAccountId());
        Movements newMovements = new Movements(deposit.getAmount(), deposit.getDescription(), newDeposit.getDate(),
                newDeposit.getType());
        newMovements.getIncomings().add(newIncoming);

        account.get().setBalance(newDeposit.getAmount());
        account.get().getDeposits().add(newDeposit);
        account.get().getMovements().add(newMovements);

        return depositRepository.save(newDeposit);

    }

}
