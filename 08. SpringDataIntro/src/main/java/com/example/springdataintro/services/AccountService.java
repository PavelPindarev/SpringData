package com.example.springdataintro.services;

import com.example.springdataintro.models.Account;

import java.math.BigDecimal;
import java.rmi.NoSuchObjectException;

public interface AccountService {
    void withdrawMoney(BigDecimal money, Long id) throws NoSuchObjectException;
    void depositMoney(BigDecimal money, Long id);
    void save(Account account);
}
