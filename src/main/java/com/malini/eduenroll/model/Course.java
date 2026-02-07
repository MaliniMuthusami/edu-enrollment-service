package com.malini.eduenroll.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "courses")
public class Course {
    @Id // _id
    private String id;
    private String title;
    @Indexed(unique = true)
    private String code;
    private boolean active;

    public Course(boolean active, String id, String title, String code) {
        this.active = active;
        this.id = id;
        this.title = title;
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", code='" + code + '\'' +
                ", active=" + active +
                '}';
    }
}
