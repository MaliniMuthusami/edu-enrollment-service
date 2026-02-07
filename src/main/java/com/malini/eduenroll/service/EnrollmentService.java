package com.malini.eduenroll.service;

import com.malini.eduenroll.dto.PaginatedResponse;
import com.malini.eduenroll.exception.EnrollmentNotFoundException;
import com.malini.eduenroll.exception.EnrollmentStatusNotActiveException;
import com.malini.eduenroll.model.Enrollment;
import com.malini.eduenroll.repo.EnrollmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepo;

    public EnrollmentService(EnrollmentRepository enrollmentRepo) {
        this.enrollmentRepo = enrollmentRepo;
    }

    public Enrollment getEnrollmentById(String id){
        return enrollmentRepo.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException(id));
    }

    public PaginatedResponse<Enrollment> searchEnrollments
            (String search, String status, Instant enrolledAt, String sortBy, String sortDir,
             Integer page, Integer size){
        int safePage = (page == null || page < 0) ? 0 : page;
        int safeSize = (size == null || size < 0) ? 3 : size;

        String effectiveSortBy = (sortBy == null || sortBy.isBlank()) ? "studentId" : sortBy;
        String effectiveSortDir = (sortDir == null || sortDir.isBlank()) ? "asc" : sortDir;

        if(!effectiveSortBy.equalsIgnoreCase("studentId")
        && !effectiveSortBy.equalsIgnoreCase("courseId")
        && !effectiveSortBy.equalsIgnoreCase("status")
        && !effectiveSortBy.equalsIgnoreCase("enrolledAt")){
            throw new IllegalArgumentException("Invalid sort by: "+ effectiveSortBy);
        }

        Sort.Direction direction = effectiveSortDir.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(safePage, safeSize, Sort.by(direction, effectiveSortBy));
        String keyword = (search == null) ? "" : search.trim();
        Page<Enrollment> result;

        /*
        filter options
        1. status AND enrolledAt AND search
        2. status AND search
        3. enrolledAt AND search
        4. status
        5. enrolledAt
        6. search
        7. no filters
         */
        if(status != null && enrolledAt != null && !keyword.isBlank()){
            result = enrollmentRepo.findByStatusAndEnrolledAtAndCourseIdIgnoreCaseOrStatusAndEnrolledAtAndStudentIdIgnoreCase
                    (status, enrolledAt, keyword, status, enrolledAt, keyword, pageable);
        }else if(status != null && !keyword.isBlank()){
            result = enrollmentRepo.findByStatusAndCourseIdIgnoreCaseOrStatusAndStudentIdIgnoreCase
                    (status, keyword, status, keyword, pageable);
        }else if(enrolledAt != null && !keyword.isBlank()){
            result = enrollmentRepo.findByEnrolledAtAndCourseIdIgnoreCaseOrEnrolledAtAndStudentIdIgnoreCase
                    (enrolledAt, keyword, enrolledAt, keyword, pageable);
        }else if(status != null){
            result = enrollmentRepo.findByStatus(status, pageable);
        }else if(enrolledAt != null){
            result = enrollmentRepo.findByEnrolledAt(enrolledAt, pageable);
        }else if(!keyword.isBlank()){
            result = enrollmentRepo.findByCourseIdIgnoreCaseOrStudentIdIgnoreCase
                    (keyword, keyword, pageable);
        }else{
            result = enrollmentRepo.findAll(pageable);
        }

        PaginatedResponse.Metadata meta = new PaginatedResponse.Metadata
                (safePage, safeSize, result.getTotalElements(), result.getTotalPages(),
                        effectiveSortBy, effectiveSortDir);

        return new PaginatedResponse<>(result.getContent(), meta);
    }

    public Enrollment createEnrollment(String studentId, String courseId){
        Enrollment enrollment = new Enrollment(null, studentId, courseId, Instant.now(), "ACTIVE");
        return enrollmentRepo.save(enrollment);
    }

    public Enrollment updateEnrollment(String id, String studentId, String courseId, String status){
        Enrollment existing = enrollmentRepo.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException(id));
        if(existing.getStatus().equalsIgnoreCase("ACTIVE")){
        existing.setCourseId(courseId);
        existing.setStudentId(studentId);
        existing.setStatus(status);
        }else{
            throw new EnrollmentStatusNotActiveException(id, status);
        }
        return existing;
    }

    public void deleteEnrollment(String id){
        if(!enrollmentRepo.existsById(id)){
            throw new EnrollmentNotFoundException(id);
        }
        enrollmentRepo.deleteById(id);
    }
}
