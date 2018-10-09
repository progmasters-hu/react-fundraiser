package com.progmasters.fundraiser.domain;

public enum CurrencyType {

    HUF("HUF"),
    EUR("EUR"),
    USD("USD"),
    GBP("GBP");

    private String displayName;

    CurrencyType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
