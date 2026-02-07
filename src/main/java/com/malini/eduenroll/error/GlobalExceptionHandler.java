package com.malini.eduenroll.error;

import java.util.Map;

import com.guvi.spring_boot_intro.exception.*;
import com.malini.eduenroll.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // DuplicateEmailException

    // person A tries to sign up with email: a@gmail.com -> success
    // person B tries to sign up with email: a@gmail.com -> failure -> 409 Conflict

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateEmail(DuplicateEmailException ex) {
        // mapping an exception to a HTTP response
        // { "message": "Email already exists: a@gmail.com" }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Map.of("message", ex.getMessage())
        );
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity handleStudentNotFound(StudentNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body( Map.of("message", ex.getMessage()));

    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity handleCourseNotFound(CourseNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body( Map.of("message", ex.getMessage()));

    }
    @ExceptionHandler(DuplicateCourseException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateCourse(DuplicateCourseException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Map.of("message", ex.getMessage())
        );
    }

    @ExceptionHandler(EnrollmentNotFoundException.class)
    public ResponseEntity handleEnrollmentNotFound(EnrollmentNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body( Map.of("message", ex.getMessage()));

    }

    @ExceptionHandler(EnrollmentStatusNotActiveException.class)
    public ResponseEntity handleEnrollmentNotActive(EnrollmentStatusNotActiveException ex) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                Map.of("message", ex.getMessage())
        );
    }
}
