package com.emdev.wallet.controller;

import com.emdev.wallet.model.Account;
import com.emdev.wallet.service.IAccountService;
import com.emdev.wallet.utils.Mensaje;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@Tag(name="2. Accounts endpoints",description = "Create/Get/Delete accouts endpoints")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    // get all user accounts
    @Operation(summary = "Get all user accounts", description = "Get all user accounts by user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieve all accounts"),
    })
    @GetMapping("/getAccounts/id/{id}")
    public ResponseEntity<List<Account>> getAllUserAccounts(@PathVariable("id") Integer id) {
        List<Account> userAccounts = accountService.getUserAccounts(id);
        return ResponseEntity.ok(userAccounts);
    }

    // get one user account
    @Operation(summary = "Get one user account", description = "Get one user account by account id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieve account"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
    })
    @GetMapping("/getAccount/id/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable("id") Integer id) {
        Account userAccount = accountService.getAccount(id);
        return ResponseEntity.ok(userAccount);
    }

    // create a new account
    @Operation(summary = "Create new account", description = "Create new account by user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully create account"),
    })
    @PostMapping("/newAccount/{id}")
    public ResponseEntity<Account> createAccount(@PathVariable("id") Integer id) {
        Account newAccount = accountService.createAccount(id);
        return ResponseEntity.ok(newAccount);
    }

    // delete one account
    @Operation(summary = "Delete an account", description = "Deleted an account by account id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully delete account"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
    })
    @DeleteMapping("/deleteAccount/{id}")
    public ResponseEntity<Mensaje> deleteAccount(@PathVariable("id") Integer id) {
        accountService.deleteAccount(id);
        return ResponseEntity.status(HttpStatus.OK).body(new Mensaje("Successfully delete account"));
    }
}
