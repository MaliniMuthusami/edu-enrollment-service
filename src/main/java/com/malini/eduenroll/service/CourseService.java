package com.malini.eduenroll.service;

import com.malini.eduenroll.dto.PaginatedResponse;
import com.malini.eduenroll.exception.CourseNotFoundException;
import com.malini.eduenroll.exception.DuplicateCourseException;
import com.malini.eduenroll.model.Course;
import com.malini.eduenroll.repo.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepo;

    public CourseService(CourseRepository courseRepo) {
        this.courseRepo = courseRepo;
    }

    public Course getCourseById(String id){
        return courseRepo.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
    }

    public PaginatedResponse<Course> searchCourses(String search, Boolean active, String sortBy,
                                                   String sortDir, Integer page, Integer size){
        int safePage = (page == null || page < 0) ? 0 : page;
        int safeSize = (size == null || size < 0) ? 3 : size;

        String effectiveSortBy = (sortBy == null || sortBy.isBlank()) ? "title" : sortBy;
        String effectiveSortDir = (sortDir == null || sortDir.isBlank()) ? "asc" : sortDir;

        if(!effectiveSortBy.equalsIgnoreCase("title")
                && !effectiveSortBy.equalsIgnoreCase("code")
                && !effectiveSortBy.equalsIgnoreCase("active") ){
            throw new IllegalArgumentException("Invalid SortBy:" + effectiveSortBy);
        }

        Sort.Direction direction = effectiveSortDir.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(safePage, safeSize,Sort.by(direction, effectiveSortBy));
        String keyword = (search == null) ? "" : search.trim();

        Page<Course> result;

        /*
        1. active AND keyword: filter + search together
        2. keyword: only search
        3. active: only filter
        4. no filter: plain list
         */
        if(active!=null && !keyword.isBlank()) {
            result = courseRepo.findByActiveAndTitleContainingIgnoreCaseOrActiveAndCodeContainingIgnoreCase
                    (active, keyword, active, keyword, pageable);
        }else if(!keyword.isBlank()) {
            result = courseRepo.findByTitleContainingIgnoreCaseOrCodeContainingIgnoreCase
                    (keyword, keyword, pageable);
        }else if(active != null) {
            result = courseRepo.findByActive(active, pageable);
        }else {
            result = courseRepo.findAll(pageable);
        }

        PaginatedResponse.Metadata meta = new PaginatedResponse.Metadata
                (safePage, safeSize, result.getTotalElements(), result.getTotalPages(),
                        effectiveSortBy, effectiveSortDir);

        return new PaginatedResponse<>(result.getContent(), meta);
    }

    public Course createCourse(String title, String code){
        courseRepo.findByCodeIgnoreCase(code)
                .ifPresent(existing ->{ throw new DuplicateCourseException(code);}
                );
        Course course = new Course(true, null, title, code);
        courseRepo.save(course);
        return course;
    }

    public Course updateCourse(String id, String title, String code) {
        Course existing = courseRepo.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
        //code must be unique
        String currentCode = existing.getCode();
        if (code != null && !currentCode.equalsIgnoreCase(code)) {
            Optional<Course> duplicate = courseRepo.findByCodeIgnoreCase(code);
            if (duplicate.isPresent() && !duplicate.get().getId().equals(id)) {
                throw new DuplicateCourseException(code);
            }
        }

        existing.setCode(code);
        existing.setTitle(title);

        return courseRepo.save(existing);
    }

    public void deleteCourseById(String id){
        if(!courseRepo.existsById(id)){
            throw new CourseNotFoundException(id);
        }
        courseRepo.deleteById(id);
    }

}

