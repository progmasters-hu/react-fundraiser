package com.progmasters.fundraiser.domain.dto;

public class TransferConfirmation {

    private Long id;

    private Integer confirmationCode;

    public void setId(Long id) {
        this.id = id;
    }

    public void setConfirmationCode(Integer confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public Long getId() {
        return id;
    }

    public Integer getConfirmationCode() {
        return confirmationCode;
    }
}
