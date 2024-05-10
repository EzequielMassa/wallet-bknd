package com.emdev.wallet.controller;

import com.emdev.wallet.model.Transfer;
import com.emdev.wallet.service.ITransferService;
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
@RequestMapping("/api/v1/transfers")
@Tag(name = "5. Transfer endpoints", description = "Create/Get transfers endpoints")
public class TransferController {

    @Autowired
    private ITransferService transferService;

    // get all user transfers
    @Operation(summary = "Get all user transfers", description = "Get all user transfers by account id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieve all transfers"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
    })
    @GetMapping("/getTransfers/id/{id}")
    public ResponseEntity<List<Transfer>> getAllAccountTransfers(@PathVariable("id") Integer id){
        List<Transfer> accountTransfers = transferService.getAccountTransfers(id);
        return ResponseEntity.ok(accountTransfers);
    }


    // get one user transfer
    @Operation(summary = "Get one user transfer", description = "Get one user transfer by transfer id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieve transfer"),
            @ApiResponse(responseCode = "404", description = "Transfer not found"),
    })
    @GetMapping("/getTransfer/id/{id}")
    public ResponseEntity<Transfer> getAccountTransfer(@PathVariable("id") Integer id){
        Transfer accountTransfer = transferService.getTransfer(id);
        return ResponseEntity.ok(accountTransfer);
    }

    // create new transfer
    @Operation(summary = "Create new transfer", description = "Create new transfer by Origin and Destiny accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created transfer"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "402", description = "Insufficient balance"),
    })
    @PostMapping("/newTransfer/origin/{accountOriginId}/destiny/{accountDestinyId}")
    public ResponseEntity<Transfer> createDeposit(@PathVariable("accountOriginId") Integer accountOriginId ,@PathVariable("accountDestinyId") Integer accountDestinyId ,@Valid  @RequestBody Transfer transfer) throws Exception {

            Transfer newTransfer = transferService.createTransfer(accountOriginId,accountDestinyId,transfer);
            return new ResponseEntity<>(newTransfer, HttpStatus.OK);

    }

}
