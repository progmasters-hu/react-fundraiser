package com.progmasters.fundraiser.controller;

import com.progmasters.fundraiser.domain.Account;
import com.progmasters.fundraiser.domain.CurrencyType;
import com.progmasters.fundraiser.domain.dto.AccountCurrencyData;
import com.progmasters.fundraiser.domain.dto.AccountDetails;
import com.progmasters.fundraiser.domain.dto.AccountRegistrationCommand;
import com.progmasters.fundraiser.security.AuthenticatedUserDetails;
import com.progmasters.fundraiser.service.AccountService;
import com.progmasters.fundraiser.service.HourlyRatesService;
import com.progmasters.fundraiser.service.TransferService;
import com.progmasters.fundraiser.validation.AccountRegistrationCommandValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private AccountService accountService;
    private TransferService transferService;
    private AccountRegistrationCommandValidator accountRegistrationCommandValidator;
    private HourlyRatesService hourlyRatesService;

    @Autowired
    public AccountController(AccountService accountService, TransferService transferService, AccountRegistrationCommandValidator accountRegistrationCommandValidator,  HourlyRatesService hourlyRatesService) {
        this.accountService = accountService;
        this.transferService = transferService;
        this.accountRegistrationCommandValidator = accountRegistrationCommandValidator;
        this.hourlyRatesService = hourlyRatesService;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(accountRegistrationCommandValidator);
    }

    @GetMapping
    public ResponseEntity<List<AccountDetails>> getAllAccounts() {
        List<AccountDetails> accountDetails = new ArrayList<>();
        List<Account> accounts = accountService.findAll();
        for (Account account : accounts) {
            accountDetails.add(new AccountDetails(account));
        }
        Collections.sort(accountDetails);

        return new ResponseEntity<>(accountDetails, HttpStatus.OK);
    }

    @GetMapping("/myAccountDetails")
    public ResponseEntity<AccountDetails> getMyAccountDetails(Principal principal) {
        Account myAccount = accountService.findByUserName(principal.getName());
        if (myAccount != null) {
            AccountDetails myAccountDetails = new AccountDetails(myAccount);
            myAccountDetails.setSourceTransfers(transferService.getMySourceTransfers(myAccount));
            myAccountDetails.setTargetTransfers(transferService.getMyTargetTransfers(myAccount));
            return new ResponseEntity<>(myAccountDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/transferFunds/{id}")
    public ResponseEntity <Account> getAccount(@PathVariable Long id){
        return new ResponseEntity<>(accountService.findOne(id), HttpStatus.OK);
    }

    @GetMapping("/currencies")
    public ResponseEntity<AccountCurrencyData> getCurrencies(){
        Map<String, String> currencies = new LinkedHashMap<>();
        for(CurrencyType currencyType : CurrencyType.values()){
            currencies.put(currencyType.name(), currencyType.getDisplayName());
        }

        AccountCurrencyData accountCurrencyData = new AccountCurrencyData(currencies);

        return new ResponseEntity<>(accountCurrencyData, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> registerNewAccount(@RequestBody @Valid AccountRegistrationCommand accountRegistrationCommand) {
        Map<String, Double> rates = hourlyRatesService.getActualRates().getRates();
        accountService.create(accountRegistrationCommand, rates);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/me")
    public ResponseEntity<AuthenticatedUserDetails> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();

        return new ResponseEntity<>(new AuthenticatedUserDetails(user), HttpStatus.OK);
    }

    @GetMapping(value = "/myId")
    public ResponseEntity<Long> getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();

        Account account = accountService.findByUserName(user.getUsername());
        Long id = account.getId();

        return new ResponseEntity<>(id, HttpStatus.OK);
    }



}