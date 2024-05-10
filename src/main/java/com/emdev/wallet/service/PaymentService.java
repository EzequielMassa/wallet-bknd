package com.emdev.wallet.service;

import com.emdev.wallet.model.Payment;

import java.util.List;


public interface PaymentService {
    List<Payment> getAccountPayments(Integer accountId);
    Payment getPayment(Integer paymentId);

    Payment createPayment(Integer accountId, Payment payment) throws Exception;
}
