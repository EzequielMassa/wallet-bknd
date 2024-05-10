package com.emdev.wallet.controller;

import com.emdev.wallet.model.Deposit;
import com.emdev.wallet.service.IDepositService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


@RestController
@RequestMapping("/api/v1/deposits")
@Tag(name="3. Deposit endpoints",description = "Create/Get accouts deposits endpoints")
public class DepositController {

    @Autowired
    private IDepositService depositService;


    @GetMapping("/getDeposits/id/{id}")
    public ResponseEntity<List<Deposit>> getAllAccountDeposits(@PathVariable("id") Integer id){
        List<Deposit> accountDeposits = depositService.getAccountDeposits(id);
        return ResponseEntity.ok(accountDeposits);
    }

    @Operation(summary = "Get one user deposit", description = "Get one user deposit by deposit id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieve deposit"),
            @ApiResponse(responseCode = "404", description = "Deposit not found"),
    })
    @GetMapping("/getDeposit/id/{id}")
    public ResponseEntity<Deposit> getAccountDeposit(@PathVariable("id") Integer id){
        Deposit accountDeposit = depositService.getDeposit(id);
        return ResponseEntity.ok(accountDeposit);
    }

    @Operation(summary = "Create new deposit", description = "Create new deposit for account id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created deposit"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
    })
    @PostMapping("/newDeposit/{id}")
    public ResponseEntity<Deposit> createDeposit(@PathVariable("id") Integer id,@Valid  @RequestBody Deposit deposit) throws ParseException {
        Deposit newDeposit = depositService.createDeposit(id,deposit);
        return ResponseEntity.ok(newDeposit);
    }

}
