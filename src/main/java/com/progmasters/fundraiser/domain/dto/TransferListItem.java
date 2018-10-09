package com.progmasters.fundraiser.domain.dto;

import com.progmasters.fundraiser.domain.Transfer;

public class TransferListItem {

    private Long id;

    private String from;

    private String to;

    private Double amount;

    private Double targetAmount;

    private String sourceCurrency;

    private String targetCurrency;

    private String timeStamp;

    private Boolean pending;

    private Boolean isFulfilled;

    private String transactionDate;


    public TransferListItem(Transfer transfer) {
        this.id = transfer.getId();
        this.from = transfer.getFrom().getUserName();
        this.to = transfer.getTo().getGoalName();
        this.amount = transfer.getAmount();
        this.timeStamp = String.valueOf(transfer.getTimeStamp());
        this.pending = transfer.getPending();
        this.isFulfilled = transfer.getFulfilled();
        this.transactionDate = String.valueOf(transfer.getTransactionDate());
        this.targetAmount = transfer.getAmountInSendersCurrency();
        this.targetCurrency = transfer.getTargetCurrency();
        this.sourceCurrency = transfer.getSourceCurrency();
    }

    public Long getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Double getAmount() {
        return amount;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public Boolean getPending() {
        return pending;
    }

    public Boolean getFulfilled() {
        return isFulfilled;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public Double getTargetAmount() {
        return targetAmount;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }
}
