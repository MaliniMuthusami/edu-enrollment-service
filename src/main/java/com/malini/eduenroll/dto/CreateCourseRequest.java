package com.malini.eduenroll.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateCourseRequest {
    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "code is required")
    private String code;

    private boolean active;

    public CreateCourseRequest(){}
    public CreateCourseRequest(String title, String code) {
        this.title = title;
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
