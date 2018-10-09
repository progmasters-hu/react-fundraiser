package com.progmasters.fundraiser.domain;


public class ExchangeRate {

    private CurrencyType from;
    private CurrencyType to;
    private Double rate;

    public ExchangeRate() {
    }

    public ExchangeRate(CurrencyType from, CurrencyType to, Double rate) {
        this.from = from;
        this.to = to;
        this.rate = rate;
    }

    public CurrencyType getFrom() {
        return from;
    }

    public void setFrom(CurrencyType from) {
        this.from = from;
    }

    public CurrencyType getTo() {
        return to;
    }

    public void setTo(CurrencyType to) {
        this.to = to;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExchangeRate that = (ExchangeRate) o;

        if (from != that.from) return false;
        return to == that.to;
    }

    @Override
    public int hashCode() {
        int result = from != null ? from.hashCode() : 0;
        result = 31 * result + (to != null ? to.hashCode() : 0);
        return result;
    }
}
