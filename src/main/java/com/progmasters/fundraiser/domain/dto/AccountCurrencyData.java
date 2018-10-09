package com.progmasters.fundraiser.domain.dto;

import java.util.Map;

public class AccountCurrencyData {

    private Map<String, String> currencyTypes;

    public AccountCurrencyData(Map<String, String> currencyTypes) {
        this.currencyTypes = currencyTypes;
    }

    public Map<String, String> getCurrencyTypes() {
        return currencyTypes;
    }
}
