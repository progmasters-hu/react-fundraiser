package com.progmasters.fundraiser.validation;

import com.progmasters.fundraiser.domain.Account;
import com.progmasters.fundraiser.domain.dto.RateToTransfer;
import com.progmasters.fundraiser.domain.dto.TransferCreationCommand;
import com.progmasters.fundraiser.service.AccountService;
import com.progmasters.fundraiser.service.HourlyRatesService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.servlet.http.HttpServletRequest;

@Component
public class TransferCreationCommandValidator implements Validator {

    private AccountService accountService;
    private HourlyRatesService hourlyRatesServiceService;

    private HttpServletRequest request;

    public TransferCreationCommandValidator(AccountService accountService, HourlyRatesService hourlyRatesServiceService, HttpServletRequest request) {
        this.accountService = accountService;
        this.hourlyRatesServiceService = hourlyRatesServiceService;
        this.request = request;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return TransferCreationCommand.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();

        TransferCreationCommand transferCreationCommand = (TransferCreationCommand) target;
        Account from = accountService.findByUserName(user.getUsername());

        if (transferCreationCommand.getAmount() == null) {
            errors.rejectValue("amount", "transfer.amount.missing");
        } else {

            RateToTransfer rateToTransfer = hourlyRatesServiceService.getActualRates();
            double min = rateToTransfer.getRates().get(from.getCurrencyType().getDisplayName())
                    / rateToTransfer.getRates().get("USD")*50;
            double max = rateToTransfer.getRates().get(from.getCurrencyType().getDisplayName())
                    / rateToTransfer.getRates().get("USD")*1000;

            double transferAmount = transferCreationCommand.getAmount();

            if (from.getBalance() - transferAmount < 0) {
                errors.rejectValue("amount", "account.balanceNotEnough");
            } else if (transferAmount > max || transferAmount < min) {
                errors.rejectValue("amount", "transfer.amount.boundaries");
            }
        }
    }
}
