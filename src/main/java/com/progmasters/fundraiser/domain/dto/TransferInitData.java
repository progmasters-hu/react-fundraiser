package com.progmasters.fundraiser.domain.dto;

import java.util.Map;

public class TransferInitData {

    private String sourceAccountName;
    private Map<Long, String> targetAccounts;
    private Map<Long, String> targetCurrencies;
    private double balance;
    private String myCurrency;

    public TransferInitData(String sourceAccountName, Map<Long, String> targetAccounts,
                            double balance, Map<Long, String> targetCurrencies, String myCurrency) {
        this.sourceAccountName = sourceAccountName;
        this.targetAccounts = targetAccounts;
        this.balance = balance;
        this.targetCurrencies = targetCurrencies;
        this.myCurrency = myCurrency;
    }

    public String getSourceAccountName() {
        return sourceAccountName;
    }

    public void setSourceAccountName(String sourceAccountName) {
        this.sourceAccountName = sourceAccountName;
    }

    public Map<Long, String> getTargetAccounts() {
        return targetAccounts;
    }

    public void setTargetAccounts(Map<Long, String> targetAccounts) {
        this.targetAccounts = targetAccounts;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Map<Long, String> getTargetCurrencies() {
        return targetCurrencies;
    }

    public void setTargetCurrencies(Map<Long, String> targetCurrencies) {
        this.targetCurrencies = targetCurrencies;
    }

    public String getMyCurrency() {
        return myCurrency;
    }
}
