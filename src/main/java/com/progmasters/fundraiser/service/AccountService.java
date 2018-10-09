package com.progmasters.fundraiser.service;

import com.progmasters.fundraiser.domain.Account;
import com.progmasters.fundraiser.domain.dto.AccountDetails;
import com.progmasters.fundraiser.domain.dto.AccountRegistrationCommand;
import com.progmasters.fundraiser.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AccountService {

    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;
    private HourlyRatesService hourlyRatesService;

    @Autowired
    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, HourlyRatesService hourlyRatesService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.hourlyRatesService = hourlyRatesService;
    }

    public Account create(AccountRegistrationCommand accountRegistrationCommand, Map<String, Double> rates) {
        Account account = new Account(accountRegistrationCommand);

        Double rateToMultiplyOn = hourlyRatesService.getUSDExchangeRate(account.getCurrencyType(), rates);
        double val = rateToMultiplyOn * 5000;
        double roundedVal = Math.round(val * 100);

        account.setBalance(roundedVal / 100);
        account.setBalance(roundedVal / 100);
        accountRepository.save(account);
        account.setPassword(passwordEncoder.encode(accountRegistrationCommand.getPassword()));

        return account;
    }

    public Account findOne(Long id) {
        return accountRepository.findOne(id);
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public List<AccountDetails> getAllAccountDetailsExceptOwn(Account myAccount) {
        List<AccountDetails> accountDetails = new ArrayList<>();
        List<Account> accounts = accountRepository.findAll();
        accounts.remove(myAccount);
        for (Account account : accounts) {
            accountDetails.add(new AccountDetails(account));
        }

        return accountDetails;
    }

    public Account findByUserName(String name) {
        return accountRepository.findByUserName(name);
    }
}
