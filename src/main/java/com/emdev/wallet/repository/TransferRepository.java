package com.emdev.wallet.repository;

import com.emdev.wallet.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransferRepository extends JpaRepository<Transfer,Integer> {

}
