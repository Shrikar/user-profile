package com.exostar.userprofile.Exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IllegalCountryCodeException extends Exception {
    private String message;
    public IllegalCountryCodeException(String message) {
        super(message);
        this.message = message;
    }
}
