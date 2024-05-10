package com.emdev.wallet.controller;

import java.util.List;

import com.emdev.wallet.model.Expenses;
import com.emdev.wallet.model.Incomings;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emdev.wallet.model.Movements;
import com.emdev.wallet.service.IMovementsService;

@RestController
@RequestMapping("/api/v1/movements")
@Tag(name="6. Account movements endpoints",description = "Get account movements endpoints")
public class MovementsController {

    @Autowired
    private IMovementsService movementsService;

    // get all user account movements
    @Operation(summary = "Get all account movements", description = "Get all account movements by account id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieve all movements"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<List<Movements>> getAllAccountMovements(@PathVariable("id") Integer id) {
        List<Movements> accountMovements = movementsService.getAccountMovements(id);
        return ResponseEntity.ok(accountMovements);
    }

    //get all user account incomings
    @Operation(summary = "Get all account incomings", description = "Get all account incomings by account id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieve all incomings"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
    })
    @GetMapping("/incomings/id/{id}")
    public ResponseEntity<List<Incomings>> getAllAccountIncomings(@PathVariable("id") Integer id) {
        List<Incomings> accountIncomings = movementsService.getAccountIncomings(id);
        return ResponseEntity.ok(accountIncomings);
    }

    //get all user account incomings by year
    @Operation(summary = "Get all account incomings by year", description = "Get all account incomings by account id and year")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieve all incomings"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "400", description = "Invalid year"),
    })
    @GetMapping("/incomings/id/{id}/year/{year}")
    public ResponseEntity<List<Object>> getAllAccountIncomingsByYear(@PathVariable("id") Integer id,@PathVariable("year") Integer year) {
        List<Object> accountIncomings = movementsService.getAccountIncomingsByYear(id,year);
        return ResponseEntity.ok(accountIncomings);
    }

    //get all user account incomings by month and year
    @Operation(summary = "Get all account incomings by month and year", description = "Get all account incomings by account id , month and year")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieve all incomings"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "400", description = "Invalid date"),
    })
    @GetMapping("/incomings/id/{id}/month/{month}/year/{year}")
    public ResponseEntity<Object> getAccountIncomingsByMonthAndYear(@PathVariable("id") Integer id,@PathVariable("month") Integer month,@PathVariable("year") Integer year) {
        Object accountIncoming = movementsService.getAccountIncomingsByMonthAndYear(id,month,year);
        return ResponseEntity.ok(accountIncoming);
    }

    //get all user account expenses
    @Operation(summary = "Get all account expenses", description = "Get all account expenses by account id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieve all expenses"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
    })
    @GetMapping("/expenses/id/{id}")
    public ResponseEntity<List<Expenses>> getAllAccountExpenses(@PathVariable("id") Integer id) {
        List<Expenses> accountExpenses = movementsService.getAccountExpenses(id);
        return ResponseEntity.ok(accountExpenses);
    }

    //get all user account expenses by year
    @Operation(summary = "Get all account expenses by year", description = "Get all account expenses by account id and year")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieve all expenses"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "400", description = "Invalid year"),
    })
    @GetMapping("/expenses/id/{id}/year/{year}")
    public ResponseEntity<List<Object>> getAllAccountExpensesByYear(@PathVariable("id") Integer id,@PathVariable("year") Integer year) {
        List<Object> accountExpenses = movementsService.getAccountExpensesByYear(id,year);
        return ResponseEntity.ok(accountExpenses);
    }

    //get all user account expenses by month and year
    @Operation(summary = "Get all account expenses by month and year", description = "Get all account expenses by account id , month and year")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieve all expenses"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "400", description = "Invalid date"),
    })
    @GetMapping("/expenses/id/{id}/month/{month}/year/{year}")
    public ResponseEntity<Object> getAAccountExpensesByMonthAndYear(@PathVariable("id") Integer id,@PathVariable("month") Integer month,@PathVariable("year") Integer year) {
        Object accountExpense = movementsService.getAccountExpensesByMonthAndYear(id,month,year);
        return ResponseEntity.ok(accountExpense);
    }

}
