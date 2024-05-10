package com.emdev.wallet.repository;

import com.emdev.wallet.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {

    Optional<Account> findByAccountId(Integer accountId);

    @Query("SELECT dep FROM Account acc join acc.deposits dep WHERE acc.accountId = :id")
    List<Deposit> findDepositsByAccount(@Param("id") Integer Id);

    @Query("SELECT pay FROM Account acc join acc.payments pay WHERE acc.accountId = :id")
    List<Payment> findPaymentsByAccount(@Param("id") Integer Id);

    @Query("SELECT trans FROM Account acc join acc.transfers trans WHERE acc.accountId = :id")
    List<Transfer> findTransfersByAccount(@Param("id") Integer Id);

    @Query("SELECT mov FROM Account acc join acc.movements mov WHERE acc.accountId  = :id order by mov.date DESC")
   Optional<List<Movements>> findMovementsByAccountOrderByDateDesc(@Param("id") Integer Id);


}
