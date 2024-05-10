package com.emdev.wallet.service;

import com.emdev.wallet.model.Deposit;

import java.text.ParseException;
import java.util.List;


public interface DepositService {
    List<Deposit> getAccountDeposits(Integer accountId);
    Deposit getDeposit(Integer depositId);

    Deposit createDeposit(Integer accountId, Deposit deposit) throws ParseException;

}
