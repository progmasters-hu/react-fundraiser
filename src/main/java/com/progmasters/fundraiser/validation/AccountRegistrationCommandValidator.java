package com.progmasters.fundraiser.validation;

import com.progmasters.fundraiser.domain.dto.AccountRegistrationCommand;
import com.progmasters.fundraiser.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AccountRegistrationCommandValidator implements Validator {

    private AccountRepository accountRepository;

    @Autowired
    public AccountRegistrationCommandValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountRegistrationCommand.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountRegistrationCommand accountRegistrationCommand = (AccountRegistrationCommand) target;

        if (accountRepository.findByUserName(accountRegistrationCommand.getUserName()) != null) {
            errors.rejectValue("userName", "userName.already.used");
        }
        if (accountRegistrationCommand.getUserName().isEmpty() || accountRegistrationCommand.getUserName().equals("")) {
            errors.rejectValue("userName", "userName.notGiven");
        }
        if (accountRegistrationCommand.getEmail().isEmpty() || accountRegistrationCommand.getEmail().equals("")) {
            errors.rejectValue("email", "email.notGiven");
        }
        if (accountRegistrationCommand.getGoal().isEmpty() || accountRegistrationCommand.getGoal().equals("")) {
            errors.rejectValue("goal", "goal.notGiven");
        }
        if (accountRegistrationCommand.getGoal().length() < 4 || accountRegistrationCommand.getGoal().length() > 12) {
            errors.rejectValue("goal", "goal.boundaries");
        }
        if (accountRegistrationCommand.getGoalBody().length() < 4 || accountRegistrationCommand.getGoalBody().length() > 230) {
            errors.rejectValue("goalBody", "goalBody.boundaries");
        }
        if(accountRegistrationCommand.getUserName().length() < 4 || accountRegistrationCommand.getUserName().length() > 30){
            errors.rejectValue("userName", "userName.boundaries");
        }
        if(accountRegistrationCommand.getCurrencyType().isEmpty()){
            errors.rejectValue("currencyType", "currency.notGiven");
        }

    }
}
