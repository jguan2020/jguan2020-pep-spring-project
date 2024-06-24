package com.example.repository;
import com.example.entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{

    //@Query("select count(a) from Account a where a.username = ?1")
    @Query("select count(a) from Account a where a.username = ?1 and a.password = ?2")
    int verifyAccount(String username, String password);

    @Query("select a from Account a where a.username = ?1 and a.password = ?2")
    Account getAccountByCreds(String username, String password);

    boolean existsByUsername(String username);
}
