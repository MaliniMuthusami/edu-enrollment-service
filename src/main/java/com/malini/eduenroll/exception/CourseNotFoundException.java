package com.malini.eduenroll.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(String id) {

        super("Course Not Found" + id);
    }
}
