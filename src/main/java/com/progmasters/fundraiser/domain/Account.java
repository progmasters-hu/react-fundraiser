package com.progmasters.fundraiser.domain;

import com.progmasters.fundraiser.domain.dto.AccountRegistrationCommand;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String password;

    private String goalName;

    private Double balance;

    private Double funds;

    private String email;

    @Column(name = "goal_body", columnDefinition = "TEXT")
    private String goalBody;

    @Column(name = "img_url")
    private String imgUrl;

    @Enumerated(EnumType.STRING)
    @Column (name = "currency")
    private CurrencyType currencyType;

    @OneToMany(mappedBy = "from")
    private List<Transfer> targetTransfers = new ArrayList<>();

    @OneToMany(mappedBy = "to")
    private List<Transfer> sourceTransfers = new ArrayList<>();

    public Account() {
    }

    public Account(AccountRegistrationCommand accountRegistrationCommand) {
        this.userName = accountRegistrationCommand.getUserName();
        this.goalName = accountRegistrationCommand.getGoal();
        this.balance = 5000.0;
        this.funds = 0.0;
        this.email = accountRegistrationCommand.getEmail();
        this.password = accountRegistrationCommand.getPassword();
        this.currencyType = CurrencyType.valueOf(accountRegistrationCommand.getCurrencyType());
        this.goalBody = accountRegistrationCommand.getGoalBody();
        this.imgUrl = accountRegistrationCommand.getImgUrl();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getFunds() {
        return funds;
    }

    public void setFunds(Double funds) {
        this.funds = funds;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Transfer> getTargetTransfers() {
        return targetTransfers;
    }

    public void setTargetTransfers(List<Transfer> targetTransfers) {
        this.targetTransfers = targetTransfers;
    }

    public List<Transfer> getSourceTransfers() {
        return sourceTransfers;
    }

    public void setSourceTransfers(List<Transfer> sourceTransfers) {
        this.sourceTransfers = sourceTransfers;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public String getGoalBody() {
        return goalBody;
    }

    public void setGoalBody(String goalBody) {
        this.goalBody = goalBody;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return id != null ? id.equals(account.id) : account.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return goalName + "(" + userName + ")" + " - balance: " + balance + ", funds: " + funds;
    }
}
