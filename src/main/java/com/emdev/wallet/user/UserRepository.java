package com.emdev.wallet.user;

import com.emdev.wallet.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByTokenPassword(String tokenPassword);
    boolean existsByEmail(String email);

    @Query("SELECT acc FROM User us join us.accounts acc WHERE us.id = :id")
    List<Account> findAccountsByUser(@Param("id") Integer Id);
}
