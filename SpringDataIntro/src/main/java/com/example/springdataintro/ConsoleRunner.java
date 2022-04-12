package com.example.springdataintro;

import com.example.springdataintro.models.Account;
import com.example.springdataintro.models.User;
import com.example.springdataintro.services.AccountService;
import com.example.springdataintro.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ConsoleRunner implements CommandLineRunner {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;


    @Override
    public void run(String... args) throws Exception {
        Account account1 = new Account(BigDecimal.valueOf(50000));
        Account account2 = new Account(BigDecimal.valueOf(40000));

        accountService.save(account1);
        accountService.save(account2);
        accountService.withdrawMoney(BigDecimal.valueOf(10000), 2L);
    }
}
