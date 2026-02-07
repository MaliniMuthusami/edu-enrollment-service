package com.malini.eduenroll.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String id) {
        super("Student Not Found: " + id);
    }
}
