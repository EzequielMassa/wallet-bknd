package com.emdev.wallet.service;

import java.util.List;

import com.emdev.wallet.model.Transfer;

public interface TransferService {
    List<Transfer> getAccountTransfers(Integer accountId);

    Transfer getTransfer(Integer transferId);

    Transfer createTransfer(Integer accountOriginId, Integer accountDestinyId, Transfer transfer) throws Exception;
}
