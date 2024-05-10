package com.emdev.wallet.controller;

import com.emdev.wallet.model.Payment;
import com.emdev.wallet.service.IPaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/payments")
@Tag(name = "4. Payment endpoints", description = "Create/Get payments endpoints")
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    //get all user payments by account id
    @Operation(summary = "Get all user payments", description = "Get all user payments by account id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieve all payments"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
    })
    @GetMapping("/getPayments/id/{id}")
    public ResponseEntity<List<Payment>> getAllAccountPayments(@PathVariable("id") Integer id) {
        List<Payment> accountPayments = paymentService.getAccountPayments(id);
        return ResponseEntity.ok(accountPayments);
    }

    //get one user payment by payment id
    @Operation(summary = "Get one user payment", description = "Get one user payment by payment id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieve payment"),
            @ApiResponse(responseCode = "404", description = "Payment not found"),
    })
    @GetMapping("/getPayment/id/{id}")
    public ResponseEntity<Payment> getAccountPayment(@PathVariable("id") Integer id) {
        Payment accountPayment = paymentService.getPayment(id);
        return ResponseEntity.ok(accountPayment);
    }

    //create new payment
    @Operation(summary = "Create new payment", description = "Create new payment for account Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created payment"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "402", description = "Insufficient balance"),

    })
    @PostMapping("/newPayment/{id}")
    public ResponseEntity<Payment> createDeposit(@PathVariable("id") Integer id, @Valid @RequestBody Payment payment) throws Exception {

        Payment newPayment = paymentService.createPayment(id, payment);
        return new ResponseEntity<>(newPayment, HttpStatus.OK);

    }
}
