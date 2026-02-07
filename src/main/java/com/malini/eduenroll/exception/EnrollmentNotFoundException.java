package com.malini.eduenroll.exception;

public class EnrollmentNotFoundException extends RuntimeException {
    public EnrollmentNotFoundException(String id) {

        super("Enrollment id not found" + id);
    }
}
