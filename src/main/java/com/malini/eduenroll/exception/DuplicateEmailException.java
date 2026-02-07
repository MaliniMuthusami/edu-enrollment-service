package com.malini.eduenroll.exception;

// A custom DuplicateEmailException
    // 1. extends RunTimeException
    // 2. constructor
public class DuplicateEmailException extends RuntimeException {
    // customize the "message"
    public DuplicateEmailException(String email) {
        super("Email already exists: " + email);
    }
}
