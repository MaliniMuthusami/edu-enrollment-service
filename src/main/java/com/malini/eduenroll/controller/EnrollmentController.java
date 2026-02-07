package com.malini.eduenroll.controller;

import com.malini.eduenroll.dto.CreateEnrollmentRequest;
import com.malini.eduenroll.dto.PaginatedResponse;
import com.malini.eduenroll.model.Enrollment;
import com.malini.eduenroll.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;


    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/{id}")
    public Enrollment getEnrollmentById(@PathVariable String id){
        return enrollmentService.getEnrollmentById(id);
    }

    @GetMapping()
    public PaginatedResponse<Enrollment> listEnrollments
            (@RequestParam(required = false) String q,
             @RequestParam(required = false) String studentId,
             @RequestParam(required = false) String courseId,
             @RequestParam(required = false) Instant enrolledAt,
             @RequestParam(required = false) String status,
             @RequestParam(required = false) String sortBy,
             @RequestParam(required = false) String sortDir,
             @RequestParam(required = false) Integer page,
             @RequestParam(required = false) Integer size
            ){
        //q can be searched on studentId or courseId
        String search = q;
        if(search == null || search.isBlank()){
            if(courseId != null && !courseId.isBlank()) {
                search = courseId;
            }else if(studentId != null && !studentId.isBlank()){
                search = studentId;
            }
        }
        return enrollmentService.searchEnrollments(search, status, enrolledAt, sortBy,
                sortDir, page, size);
    }

    @PostMapping
    public ResponseEntity<Enrollment> createEnrollment(@Valid @RequestBody CreateEnrollmentRequest request){
        Enrollment enrollment = enrollmentService.createEnrollment(request.getStudentId(), request.getCourseId());
        return ResponseEntity.status(201).body(enrollment);
    }

    @PutMapping("/{id}")
    public Enrollment updateEnrollment(@PathVariable String id, @Valid @RequestBody CreateEnrollmentRequest request){
        return enrollmentService.updateEnrollment(id, request.getStudentId(), request.getCourseId(), request.getStatus());
    }

    @DeleteMapping("/{id}")
    public void deleteEnrollment(@PathVariable String id){
        enrollmentService.deleteEnrollment(id);
    }
}
