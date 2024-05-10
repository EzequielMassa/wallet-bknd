package com.emdev.wallet.service;

import com.emdev.wallet.model.Expenses;
import com.emdev.wallet.model.Incomings;
import com.emdev.wallet.model.Movements;

import java.util.List;


public interface MovementsService {
    List<Movements> getAccountMovements(Integer accountId);
    List<Incomings> getAccountIncomings(Integer accountId);
    List<Object> getAccountIncomingsByYear(Integer accountId, Integer year);
    Object getAccountIncomingsByMonthAndYear(Integer accountId,Integer month, Integer year);
    List<Expenses> getAccountExpenses(Integer accountId);
    List<Object> getAccountExpensesByYear(Integer accountId, Integer year);
    Object getAccountExpensesByMonthAndYear(Integer accountId,Integer month, Integer year);
}
