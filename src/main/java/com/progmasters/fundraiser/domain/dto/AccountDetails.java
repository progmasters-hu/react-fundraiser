package com.progmasters.fundraiser.domain.dto;

import com.progmasters.fundraiser.domain.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountDetails implements Comparable<AccountDetails> {

    private Long id;
    private String userName;
    private String goal;
    private Double balance;
    private Double funds;
    private List<TransferListItem> targetTransfers = new ArrayList<>();
    private List<TransferListItem> sourceTransfers = new ArrayList<>();
    private String currencyType;
    private String goalBody;
    private String imgUrl;

    public AccountDetails(Account account) {
        this.id = account.getId();
        this.userName = account.getUserName();
        this.goal = account.getGoalName();
        this.balance = account.getBalance();
        this.funds = account.getFunds();
        this.currencyType = account.getCurrencyType().getDisplayName();
        this.goalBody = account.getGoalBody();
        this.imgUrl = account.getImgUrl();
    }

    public void setTargetTransfers(List<TransferListItem> targetTransfers) {
        this.targetTransfers = targetTransfers;
    }

    public void setSourceTransfers(List<TransferListItem> sourceTransfers) {
        this.sourceTransfers = sourceTransfers;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getGoal() {
        return goal;
    }

    public Double getBalance() {
        return balance;
    }

    public Double getFunds() {
        return funds;
    }

    public List<TransferListItem> getTargetTransfers() {
        return targetTransfers;
    }

    public List<TransferListItem> getSourceTransfers() {
        return sourceTransfers;
    }

    public String getGoalBody() {
        return goalBody;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    @Override
    public int compareTo(AccountDetails otherAccountDetails) {
        return otherAccountDetails.funds.compareTo(this.funds);
    }
}
