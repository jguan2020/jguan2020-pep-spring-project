package com.example.service;
import com.example.repository.AccountRepository;
import com.example.entity.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    public AccountService(){
    }

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public boolean checkAccountExists(String username){
        return accountRepository.existsByUsername(username);
    }

    public Account save(Account account){
        return accountRepository.save(account);
    }
    public Account registerAccount(Account account){
        return accountRepository.save(account);
    }

    public boolean verifyAccount(Account account){
        return accountRepository.verifyAccount(account.getUsername(), account.getPassword()) > 0;
    }

    public Account getAccountByCreds(Account account){
        return accountRepository.getAccountByCreds(account.getUsername(), account.getPassword());
    }

    public boolean existsById(int id){
        return accountRepository.existsById(id);
    }
}


