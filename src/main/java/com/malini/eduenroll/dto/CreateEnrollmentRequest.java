package com.malini.eduenroll.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public class CreateEnrollmentRequest {
    @NotBlank(message = "studentId is required")
    private String studentId;
    @NotBlank(message = "courseId is required")
    private String courseId;
    private Instant enrolledAt;
    private String status;

    public CreateEnrollmentRequest() {
    }

    public CreateEnrollmentRequest(String studentId, String courseId, Instant enrolledAt, String status) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrolledAt = (enrolledAt == null) ? Instant.now() : enrolledAt;
        this.status = (status == null) ? "ACTIVE" : status;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(Instant enrolledAt) {
        this.enrolledAt = enrolledAt;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
