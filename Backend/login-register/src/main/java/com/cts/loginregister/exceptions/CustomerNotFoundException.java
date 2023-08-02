package com.cts.loginregister.exceptions;

import lombok.Data;

@Data
public class CustomerNotFoundException extends Exception {
	
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
