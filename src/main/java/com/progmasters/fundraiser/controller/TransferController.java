package com.progmasters.fundraiser.controller;

import com.progmasters.fundraiser.domain.Account;
import com.progmasters.fundraiser.domain.Transfer;
import com.progmasters.fundraiser.domain.dto.*;
import com.progmasters.fundraiser.exception.ConfirmationCodeException;
import com.progmasters.fundraiser.service.AccountService;
import com.progmasters.fundraiser.service.TransferService;
import com.progmasters.fundraiser.validation.TransferCreationCommandValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private static final Logger logger = LoggerFactory.getLogger(TransferController.class);

    private TransferService transferService;
    private AccountService accountService;
    private TransferCreationCommandValidator transferCreationCommandValidator;

    @Autowired
    public TransferController(TransferService transferService, AccountService accountService, TransferCreationCommandValidator transferCreationCommandValidator) {
        this.transferService = transferService;
        this.accountService = accountService;
        this.transferCreationCommandValidator = transferCreationCommandValidator;
    }

    @InitBinder("transferCreationCommand")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(transferCreationCommandValidator);
    }

    @GetMapping
    public ResponseEntity<List<TransferListItem>> getAllTransferDTOs() {
        List<TransferListItem> transferItems = new ArrayList<>();
        List<Transfer> transfers = transferService.findAllOrderByDateDesc();
        for (Transfer transfer : transfers) {
            transferItems.add(new TransferListItem(transfer));
        }

        return new ResponseEntity<>(transferItems, HttpStatus.OK);
    }

    @GetMapping("/newTransferData")
    public ResponseEntity<TransferInitData> newTransferData(Principal principal) {
        Account myAccount = accountService.findByUserName(principal.getName());
        List<AccountDetails> otherAccounts = accountService.getAllAccountDetailsExceptOwn(myAccount);
        TransferInitData initData = new TransferInitData(myAccount.getUserName(),
                otherAccounts.stream().collect(Collectors.toMap(AccountDetails::getId, AccountDetails::getGoal)),
                myAccount.getBalance(),
                otherAccounts.stream().collect(Collectors.toMap(AccountDetails::getId, AccountDetails::getCurrencyType)),
                myAccount.getCurrencyType().getDisplayName());

        return new ResponseEntity<>(initData, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveTransfer(@Valid @RequestBody TransferCreationCommand transferCreationCommand, Principal principal) {
        TransferListItem transferListItem = new TransferListItem(transferService.saveTransfer(transferCreationCommand, principal));

        return new ResponseEntity<>(transferListItem,HttpStatus.CREATED);
    }

    @PostMapping("/confirmation")
    public ResponseEntity<?> confirmationTransfer(@RequestBody TransferConfirmation transferConfirmation) throws ConfirmationCodeException {
        transferService.confirm(transferConfirmation);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}