package com.emdev.wallet.service;

import com.emdev.wallet.exceptions.RequestException;
import com.emdev.wallet.model.Account;
import com.emdev.wallet.model.Expenses;
import com.emdev.wallet.model.Movements;
import com.emdev.wallet.model.Payment;
import com.emdev.wallet.repository.AccountRepository;
import com.emdev.wallet.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class IPaymentService implements PaymentService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public List<Payment> getAccountPayments(Integer accountId) {
        Optional<Account> account = accountRepository.findByAccountId(accountId);
        if(!account.isPresent()){
            throw new RequestException("The requested account does not exist", "P-404",
                    HttpStatus.NOT_FOUND);
        }
        return accountRepository.findPaymentsByAccount(accountId);
    }

    @Override
    public Payment getPayment(Integer paymentId) {
        Optional<Payment> payment = paymentRepository.findById(paymentId);
        if(!payment.isPresent()){
            throw new RequestException("The requested payment does not exist", "P-404",
                    HttpStatus.NOT_FOUND);
        }

        return payment.get();
    }

    @Override
    public Payment createPayment(Integer accountId, Payment payment) throws Exception {
        Optional<Account> account = accountRepository.findByAccountId(accountId);
        if(!account.isPresent()){
            throw new RequestException("The requested account does not exist", "P-404",
                    HttpStatus.NOT_FOUND);
        }

        Payment newPayment = new Payment(payment.getAmount(),payment.getDescription());

        if(account.get().getBalance() < newPayment.getAmount()) {
            throw new RequestException("Insufficient balance", "P-402",
                    HttpStatus.PAYMENT_REQUIRED);
        }

        try {
            Expenses newExpense = new Expenses(newPayment.getDate(),newPayment.getAmount(),account.get().getAccountId());
            Movements newMovements = new Movements(newPayment.getAmount(), newPayment.getDescription(), newPayment.getDate() ,newPayment.getType());
            newMovements.getExpenses().add(newExpense);
            account.get().setPayment(newPayment.getAmount());
            account.get().getPayments().add(newPayment);
            account.get().getMovements().add(newMovements);

            return paymentRepository.save(newPayment);
        } catch (Exception e) {
            throw e;
        }

    }

}
