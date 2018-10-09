package com.progmasters.fundraiser.security;

import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticatedUserDetails {

    private String userName;

    public AuthenticatedUserDetails() {
    }

    public AuthenticatedUserDetails(UserDetails user) {
        this.userName = user.getUsername();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(UserDetails user) {
        this.userName = user.getUsername();
    }
}
