package com.progmasters.fundraiser.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
public class HourlyRates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime time;

    @Enumerated(EnumType.STRING)
    private CurrencyType baseCurrency;

    @ElementCollection
    private Map<CurrencyType, Double> rates = new HashMap<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public CurrencyType getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrencyType(CurrencyType baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public Map<CurrencyType, Double> getRates() {
        return rates;
    }

    public void setRates(Map<CurrencyType, Double> rates) {
        this.rates = rates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HourlyRates that = (HourlyRates) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
