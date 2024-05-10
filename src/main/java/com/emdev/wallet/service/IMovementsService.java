package com.emdev.wallet.service;


import com.emdev.wallet.exceptions.RequestException;
import com.emdev.wallet.model.Account;
import com.emdev.wallet.model.Expenses;
import com.emdev.wallet.model.Incomings;
import com.emdev.wallet.model.Movements;
import com.emdev.wallet.repository.AccountRepository;
import com.emdev.wallet.repository.ExpensesRepository;
import com.emdev.wallet.repository.IncomingsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class IMovementsService implements MovementsService{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private IncomingsRepository incomingsRepository;

    @Autowired
    private ExpensesRepository expensesRepository;

    @Override
    public List<Movements> getAccountMovements(Integer accountId) {
        Optional<Account> account = accountRepository.findByAccountId(accountId);
        Optional<List<Movements>> movements = accountRepository.findMovementsByAccountOrderByDateDesc(accountId);
        if (!account.isPresent()) {
            throw new RequestException("The requested account does not exist", "P-404",
                    HttpStatus.NOT_FOUND);
        }
       return movements.get();
    }
    @Override
    public List<Incomings> getAccountIncomings(Integer accountId) {
        Optional<Account> account = accountRepository.findByAccountId(accountId);
        Optional<List<Incomings>> incomings = incomingsRepository.findAllIncomingsByAccountId(accountId);
        if (!account.isPresent()) {
            throw new RequestException("The requested account does not exist", "P-404",
                    HttpStatus.NOT_FOUND);
        }
        return incomings.get();
    }

    @Override
    public List<Object> getAccountIncomingsByYear(Integer accountId, Integer year) {
        Optional<Account> account = accountRepository.findByAccountId(accountId);
        if (!account.isPresent()) {
            throw new RequestException("The requested account does not exist", "P-404",
                    HttpStatus.NOT_FOUND);
        }
        if(year > Year.now().getValue()){
            throw new RequestException("Invalid year", "P-400",
                    HttpStatus.BAD_REQUEST);
        }
        Optional<List<Object>> incomings = incomingsRepository.findAllIncomingsByYear(accountId,year);

        return incomings.get();
    }
    @Override
    public Object getAccountIncomingsByMonthAndYear(Integer accountId,Integer month, Integer year) {
        Optional<Account> account = accountRepository.findByAccountId(accountId);
        if (!account.isPresent()) {
            throw new RequestException("The requested account does not exist", "P-404",
                    HttpStatus.NOT_FOUND);
        }
        LocalDate currentdate = LocalDate.now();

        if((year > Year.now().getValue()) || ((year == Year.now().getValue()) && (month > currentdate.getMonthValue())) ) {
            throw new RequestException("Invalid date", "P-400",
                    HttpStatus.BAD_REQUEST);
        }
        return incomingsRepository.findIncomingsByMonthAndYear(accountId,month,year).orElse(null);
    }

    @Override
    public List<Expenses> getAccountExpenses(Integer accountId) {
        Optional<Account> account = accountRepository.findByAccountId(accountId);
        if (!account.isPresent()) {
            throw new RequestException("The requested account does not exist", "P-404",
                    HttpStatus.NOT_FOUND);
        }
        return expensesRepository.findAllExpensesByAccountId(accountId).orElse(null);
    }

    @Override
    public List<Object> getAccountExpensesByYear(Integer accountId, Integer year) {
        Optional<Account> account = accountRepository.findByAccountId(accountId);
        if (!account.isPresent()) {
            throw new RequestException("The requested account does not exist", "P-404",
                    HttpStatus.NOT_FOUND);
        }
        if(year > Year.now().getValue()){
            throw new RequestException("Invalid year", "P-400",
                    HttpStatus.BAD_REQUEST);
        }
        return expensesRepository.findAllExpensesByYear(accountId,year).orElse(null);
    }

    @Override
    public Object getAccountExpensesByMonthAndYear(Integer accountId,Integer month, Integer year) {
        Optional<Account> account = accountRepository.findByAccountId(accountId);
        if (!account.isPresent()) {
            throw new RequestException("The requested account does not exist", "P-404",
                    HttpStatus.NOT_FOUND);
        }
        LocalDate currentdate = LocalDate.now();

        if((year > Year.now().getValue()) || ((year == Year.now().getValue()) && (month > currentdate.getMonthValue())) ) {
            throw new RequestException("Invalid date", "P-400",
                    HttpStatus.BAD_REQUEST);
        }
        return expensesRepository.findExpensesByMonthAndYear(accountId,month,year).orElse(null);
    }

}
