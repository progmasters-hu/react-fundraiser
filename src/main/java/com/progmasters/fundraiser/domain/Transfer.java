package com.progmasters.fundraiser.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transfer_from", nullable = false)
    private Account from;

    @ManyToOne
    @JoinColumn(name = "transfer_to", nullable = false)
    private Account to;

    private Double amount = 0.0;

    private Double targetAmount = 0.0;

    private String targetCurrency;

    private String sourceCurrency;

    private Double amountInSendersCurrency = 0d;

    private Timestamp timeStamp;

    private Integer confirmationCode;

    private Boolean isPending;

    private Boolean isFulfilled;

    private Timestamp transactionDate;

    private Boolean isTimed;

    public Transfer() {
        this.isPending = true;
        this.isFulfilled = false;
        this.confirmationCode = (int)(Math.random()*(9999 - 1000)) + 1000;
        this.isTimed = false;
    }

    public Integer getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(Integer confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public Boolean getPending() {
        return isPending;
    }

    public void setPending(Boolean pending) {
        isPending = pending;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getFrom() {
        return from;
    }

    public void setFrom(Account from) {
        this.from = from;
    }

    public Account getTo() {
        return to;
    }

    public void setTo(Account to) {
        this.to = to;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Boolean getFulfilled() {
        return isFulfilled;
    }

    public void setFulfilled(Boolean fulfilled) {
        isFulfilled = fulfilled;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public Double getAmountInSendersCurrency() {
        return amountInSendersCurrency;
    }

    public void setAmountInSendersCurrency(Double amountInSendersCurrency) {
        this.amountInSendersCurrency = amountInSendersCurrency;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Boolean getTimed() {
        return this.isTimed;
    }

    public void setTimed(Boolean timed) {
        this.isTimed = timed;
    }

    public Double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(Double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transfer transfer = (Transfer) o;

        return id != null ? id.equals(transfer.id) : transfer.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
