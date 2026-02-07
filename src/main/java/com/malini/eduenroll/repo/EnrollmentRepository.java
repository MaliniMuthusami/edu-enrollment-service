package com.malini.eduenroll.repo;

import com.malini.eduenroll.model.Enrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;

public interface EnrollmentRepository extends MongoRepository<Enrollment, String> {
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
    Page<Enrollment> findByStatusAndEnrolledAtAndCourseIdIgnoreCaseOrStatusAndEnrolledAtAndStudentIdIgnoreCase
    (String status, Instant enrolledAt, String courseId,
     String status1, Instant enrolledAt1, String studentId, Pageable pageable);

    Page<Enrollment> findByStatusAndCourseIdIgnoreCaseOrStatusAndStudentIdIgnoreCase
            (String status, String courseId,
             String status1, String studentId, Pageable pageable);

    Page<Enrollment> findByEnrolledAtAndCourseIdIgnoreCaseOrEnrolledAtAndStudentIdIgnoreCase
            (Instant enrolledAt, String courseId,
             Instant enrolledAt1, String studentId, Pageable pageable);

    Page<Enrollment> findByStatus(String status, Pageable pageable);

    Page<Enrollment> findByEnrolledAt(Instant enrolledAt, Pageable pageable);

    Page<Enrollment> findByCourseIdIgnoreCaseOrStudentIdIgnoreCase
            (String courseId, String studentId, Pageable pageable);
}
