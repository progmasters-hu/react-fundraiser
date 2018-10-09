package com.progmasters.fundraiser.service;

import com.progmasters.fundraiser.domain.Account;
import com.progmasters.fundraiser.domain.Transfer;
import com.progmasters.fundraiser.domain.dto.TransferConfirmation;
import com.progmasters.fundraiser.domain.dto.TransferCreationCommand;
import com.progmasters.fundraiser.domain.dto.TransferListItem;
import com.progmasters.fundraiser.exception.ConfirmationCodeException;
import com.progmasters.fundraiser.repository.AccountRepository;
import com.progmasters.fundraiser.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TransferService {

    private TransferRepository transferRepository;
    private AccountRepository accountRepository;
    private JavaMailSender emailSender;

    @Autowired
    public TransferService(TransferRepository transferRepository, AccountRepository accountRepository, JavaMailSender email) {
        this.transferRepository = transferRepository;
        this.accountRepository = accountRepository;
        this.emailSender = email;
    }

    public Transfer saveTransfer(TransferCreationCommand transferCreationCommand, Principal principal) {
        Transfer transfer = new Transfer();
        transfer.setTimeStamp(new Timestamp(System.currentTimeMillis()));

        if (transferCreationCommand.getTransactionDate().equals("")) {
            transfer.setTransactionDate(new Timestamp(System.currentTimeMillis()));
            transfer.setTimed(false);
        } else {
            transfer.setTransactionDate(Timestamp.valueOf(transferCreationCommand.getTransactionDate()));
            transfer.setTimed(true);
        }

        Account to = accountRepository.findOne(transferCreationCommand.getTo());
        Account from = accountRepository.findByUserName(principal.getName());

        transfer.setAmount(transferCreationCommand.getAmount());
        transfer.setAmountInSendersCurrency(transferCreationCommand.getTargetAmount());
        transfer.setTo(to);
        transfer.setFrom(from);
        transfer.setSourceCurrency(from.getCurrencyType().getDisplayName());
        transfer.setTargetCurrency(to.getCurrencyType().getDisplayName());

        transferRepository.save(transfer);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(transfer.getFrom().getEmail());
        message.setFrom("team.bobok@gmail.com");
        message.setSubject("Confirmation Code");
        message.setText("This is your confirmation code " + transfer.getConfirmationCode() + "\r\nClick here to confirm transfer: http://localhost:3000/emailConfirm/" + transfer.getConfirmationCode() + "/" + transfer.getId());
        emailSender.send(message);

        return transfer;
    }

    public List<Transfer> findAllOrderByDateDesc() {
        return transferRepository.findAllByOrderByTimeStampDesc();
    }

    public List<TransferListItem> getMySourceTransfers(Account account) {
        List<TransferListItem> transferFromDTOS = new ArrayList<>();
        for (Transfer transfer : transferRepository.findAllByFromOrderByTimeStampDesc(account)) {
            transferFromDTOS.add(new TransferListItem(transfer));
        }

        return transferFromDTOS;
    }

    public List<TransferListItem> getMyTargetTransfers(Account account) {
        List<TransferListItem> transferToDTOS = new ArrayList<>();
        for (Transfer transfer : transferRepository.findAllByToOrderByTimeStampDesc(account)) {
            transferToDTOS.add(new TransferListItem(transfer));
        }

        return transferToDTOS;
    }

    public void confirm(TransferConfirmation transferConfirmation) throws ConfirmationCodeException {
        Transfer transfer = transferRepository.findById(transferConfirmation.getId());

        if (transfer.getConfirmationCode().equals(transferConfirmation.getConfirmationCode())) {
            transfer.setPending(false);
            transferRepository.save(transfer);
        } else {
            throw new ConfirmationCodeException("Invalid code.");
        }
    }

    @Scheduled(fixedRate = 5000)
    public void transfer() {
        List<Transfer> codeReset = transferRepository.findAllTruePendingAndNotFulfilledTransactions();
        for (Transfer resetTransfer: codeReset) {
            resetTransfer.setConfirmationCode(99999);
            resetTransfer.setTimed(false);
        }

        List<Transfer> transferList = transferRepository.findAllFalsePendingAndNotFulfilledTransactions();

        for (Transfer transfer : transferList) {

            Account to = transfer.getTo();
            Account from = transfer.getFrom();

            if (from.getBalance() < transfer.getAmount()) {
                transfer.setPending(true);
                transferRepository.save(transfer);
            } else {
                to.setFunds(to.getFunds() + transfer.getAmountInSendersCurrency());
                from.setBalance(from.getBalance() - transfer.getAmount());

                transfer.setFulfilled(true);
                transferRepository.save(transfer);

                List<Transfer> debitList = transferRepository.sumFutureDebit();

                Double sum = 0.0;

                for (Transfer debit : debitList) {
                    sum += debit.getAmount();
                }

                if (transfer.getFrom().getBalance() < sum) {

                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setTo(transfer.getFrom().getEmail());
                    message.setSubject("Insufficient funds.");
                    message.setText("Your current balance is not enough to process all your pending transactions.");
                    emailSender.send(message);
                }
            }
        }
    }
}
