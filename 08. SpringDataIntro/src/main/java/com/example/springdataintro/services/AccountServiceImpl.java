package com.example.springdataintro.services;

import com.example.springdataintro.models.Account;
import com.example.springdataintro.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.rmi.NoSuchObjectException;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void withdrawMoney(BigDecimal money, Long id) throws NoSuchObjectException {
        Optional<Account> accountOptional = accountRepository.findAccountById(id);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            BigDecimal balance = account.getBalance();

            if (balance.compareTo(money) >= 0) {
                balance = balance.subtract(money);
                account.setBalance(balance);
            }
        } else {
            throw new NoSuchObjectException("Cannot find a object with that id");
        }
    }

    @Override
    public void depositMoney(BigDecimal money, Long id) {

    }

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }
}
