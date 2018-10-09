package com.progmasters.fundraiser.domain.dto;

import com.progmasters.fundraiser.domain.Account;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;

public class AccountRegistrationCommand {

    @NotNull
    private String userName;

    @NotNull
    private String goal;

    @NotNull
    @Email(message = "{account.emailNotValid}")
    private String email;

    @NotNull
    private String password;

    private String goalBody;

    private String imgUrl;

    private String currencyType;

    public AccountRegistrationCommand() {
    }

    public AccountRegistrationCommand(Account account) {
        this.userName = account.getUserName();
        this.goal = account.getGoalName();
        this.email = account.getEmail();
        this.password = account.getPassword();
        this.currencyType = account.getCurrencyType().name();
        this.goalBody = account.getGoalBody();
        this.imgUrl = account.getImgUrl();
    }

    public String getUserName() {
        return userName;
    }

    public String getGoal() {
        return goal;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getGoalBody() {
        return goalBody;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
