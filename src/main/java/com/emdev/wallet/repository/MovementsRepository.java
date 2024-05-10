package com.emdev.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emdev.wallet.model.Movements;

@Repository
public interface MovementsRepository extends JpaRepository<Movements, Integer> {
}
