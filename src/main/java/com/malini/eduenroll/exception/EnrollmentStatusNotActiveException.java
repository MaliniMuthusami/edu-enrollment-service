package com.malini.eduenroll.exception;

public class EnrollmentStatusNotActiveException extends RuntimeException {
    public EnrollmentStatusNotActiveException(String id, String status) {

        super("Enrollment status for " + id + "is currently not active. Status: " + status);
    }
}
