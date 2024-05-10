package com.emdev.wallet.service;

import java.util.List;
import java.util.Optional;

import com.emdev.wallet.exceptions.RequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.emdev.wallet.model.Account;
import com.emdev.wallet.model.Expenses;
import com.emdev.wallet.model.Incomings;
import com.emdev.wallet.model.Movements;
import com.emdev.wallet.model.Transfer;
import com.emdev.wallet.repository.AccountRepository;
import com.emdev.wallet.repository.TransferRepository;
import com.emdev.wallet.types.TransactionType;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ITransferService implements TransferService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransferRepository transferRepository;

    @Override
    public List<Transfer> getAccountTransfers(Integer accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (!account.isPresent()) {
            throw new RequestException("The requested account does not exist", "P-404",
                    HttpStatus.NOT_FOUND);
        }
        return accountRepository.findTransfersByAccount(accountId);
    }

    @Override
    public Transfer getTransfer(Integer transferId) {
        Optional<Transfer> transfer = transferRepository.findById(transferId);
        if (!transfer.isPresent()) {
            throw new RequestException("The requested transfer does not exist", "P-404",
                    HttpStatus.NOT_FOUND);
        }
        return transfer.get();
    }

    @Override
    public Transfer createTransfer(Integer accountOriginId, Integer accountDestinyId, Transfer transfer)
            throws Exception {

        Optional<Account> originAccount = accountRepository.findById(accountOriginId);
        if (!originAccount.isPresent()) {
            throw new RequestException("The origin account does not exist", "P-404",
                    HttpStatus.NOT_FOUND);
        }

        Optional<Account> destinyAccount = accountRepository.findByAccountId(accountDestinyId);
        if (!destinyAccount.isPresent()) {
            throw new RequestException("The destiny account does not exist", "P-404",
                    HttpStatus.NOT_FOUND);
        }

        if (originAccount.get().getBalance() < transfer.getAmount()) {
            throw new RequestException("Insufficient balance", "P-402",
                    HttpStatus.PAYMENT_REQUIRED);
        }
        try {
            Transfer newTransferOrigin = new Transfer(transfer.getAmount(), transfer.getDescription(),
                    TransactionType.TRANSFER_OUT);
            Transfer newTransferDestiny = new Transfer(transfer.getAmount(), transfer.getDescription(),
                    TransactionType.TRANSFER_IN);
            Movements newMovementsOrigin = new Movements(transfer.getAmount(), transfer.getDescription(),
                    newTransferOrigin.getDate(),
                    newTransferOrigin.getType());
            Expenses newExpense = new Expenses(newTransferOrigin.getDate(), newTransferOrigin.getAmount(),
                    originAccount.get().getAccountId());
            newMovementsOrigin.getExpenses().add(newExpense);
            Movements newMovementsDestiny = new Movements(transfer.getAmount(), transfer.getDescription(),
                    newTransferDestiny.getDate(),
                    newTransferDestiny.getType());
            Incomings newIncoming = new Incomings(newTransferDestiny.getDate(), newTransferDestiny.getAmount(),
                    destinyAccount.get().getAccountId());
            newMovementsDestiny.getIncomings().add(newIncoming);

            originAccount.get().setPayment(transfer.getAmount());
            destinyAccount.get().setBalance(transfer.getAmount());
            originAccount.get().getTransfers().add(newTransferOrigin);
            originAccount.get().getMovements().add(newMovementsOrigin);
            destinyAccount.get().getTransfers().add(newTransferDestiny);
            destinyAccount.get().getMovements().add(newMovementsDestiny);

            transferRepository.save(newTransferDestiny);

            return transferRepository.save(newTransferOrigin);

        } catch (Exception e) {
            throw e;
        }
    }

}
