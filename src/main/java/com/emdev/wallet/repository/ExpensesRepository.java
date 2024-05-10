package com.emdev.wallet.repository;

import com.emdev.wallet.model.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ExpensesRepository  extends JpaRepository<Expenses, Integer> {
    Optional<List<Expenses>> findAllExpensesByAccountId(Integer Id);

    @Query("SELECT date_format(e.date, '%M'),sum(e.amount) FROM Expenses e WHERE e.accountId  = :accountId and year(e.date) = :year group by date_format(e.date, '%M')order by date_format(e.date, '%M') desc")
    Optional<List<Object>> findAllExpensesByYear(@Param("accountId") Integer accountId, @Param("year") Integer year);

    @Query("SELECT date_format(e.date, '%M'),sum(e.amount) FROM Expenses e WHERE e.accountId  = :accountId and month(e.date) = :month and year(e.date) = :year group by date_format(e.date, '%M')order by date_format(e.date, '%M') desc")
    Optional<Object> findExpensesByMonthAndYear(@Param("accountId") Integer accountId, @Param("month") Integer month, @Param("year") Integer year);
}
