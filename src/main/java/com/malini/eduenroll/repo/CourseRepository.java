package com.malini.eduenroll.repo;

import com.malini.eduenroll.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CourseRepository extends MongoRepository<Course, String> {

    Optional<Course> findByCodeIgnoreCase(String code);
    /*
        1. active AND keyword: filter + search together
        2. keyword: only search
        3. active: only filter
        4. no filter: plain list
         */
    Page<Course> findByActiveAndTitleContainingIgnoreCaseOrActiveAndCodeContainingIgnoreCase
    (boolean active, String titlePart, boolean active1, String codePart, Pageable pageable);

    Page<Course> findByTitleContainingIgnoreCaseOrCodeContainingIgnoreCase
            (String titlePart, String codePart, Pageable pageable);
    Page<Course> findByActive(boolean active, Pageable pageable);

}
