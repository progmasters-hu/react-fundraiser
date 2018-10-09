package com.progmasters.fundraiser.domain.dto;

import javax.validation.constraints.NotNull;

public class TransferCreationCommand {

    private Long id;

    @NotNull(message = "{transfer.to.notnull}")
    private Long to;

    private Double amount;

    private Double targetAmount;

    private String targetCurrency;

    private String sourceCurrency;

    private Double amountInMyCurrency;

    private String timeStamp;

    private String transactionDate;

    public TransferCreationCommand() {
    }

    public TransferCreationCommand(Long id, Long to, Double amount, Double targetAmount, String targetCurrency, String sourceCurrency, Double amountInMyCurrency, String timeStamp, String transactionDate) {
        this.id = id;
        this.to = to;
        this.amount = amount;
        this.targetAmount = targetAmount;
        this.targetCurrency = targetCurrency;
        this.sourceCurrency = sourceCurrency;
        this.amountInMyCurrency = amountInMyCurrency;
        this.timeStamp = timeStamp;
        this.transactionDate = transactionDate;
    }

    public Long getId() {
        return id;
    }

    public String getTransactionDate() {
        return transactionDate;
    }
    public Long getTo() {
        return to;
    }

    public Double getAmount() {
        return amount;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public Double getAmountInMyCurrency() {
        return amountInMyCurrency;
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
