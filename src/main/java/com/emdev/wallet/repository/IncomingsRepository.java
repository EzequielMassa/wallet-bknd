package com.emdev.wallet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.emdev.wallet.model.Incomings;

@Repository
public interface IncomingsRepository extends JpaRepository<Incomings, Integer> {

    Optional<List<Incomings>> findAllIncomingsByAccountId(Integer Id);

    @Query("SELECT date_format(i.date, '%M'),sum(i.amount) FROM Incomings i WHERE i.accountId  = :accountId and year(i.date) = :year group by date_format(i.date, '%M')order by date_format(i.date, '%M') desc")
    Optional<List<Object>> findAllIncomingsByYear(@Param("accountId") Integer accountId, @Param("year") Integer year);

    @Query("SELECT date_format(i.date, '%M'),sum(i.amount) FROM Incomings i WHERE i.accountId  = :accountId and month(i.date) = :month and year(i.date) = :year group by date_format(i.date, '%M')order by date_format(i.date, '%M') desc")
    Optional<Object> findIncomingsByMonthAndYear(@Param("accountId") Integer accountId, @Param("month") Integer month,
            @Param("year") Integer year);

}
