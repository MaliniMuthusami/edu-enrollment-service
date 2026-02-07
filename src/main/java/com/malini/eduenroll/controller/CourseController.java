package com.malini.eduenroll.controller;

import com.malini.eduenroll.dto.CreateCourseRequest;
import com.malini.eduenroll.dto.PaginatedResponse;
import com.malini.eduenroll.model.Course;
import com.malini.eduenroll.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable String id) {
        return courseService.getCourseById(id);
    }

    @GetMapping
    public PaginatedResponse<Course> listCourses
            (@RequestParam(required = false) String q,
             @RequestParam(required = false) String title,
             @RequestParam(required = false) String code,
             @RequestParam(required = false) Boolean active,
             @RequestParam(required = false) String sortBy,
             @RequestParam(required = false) String sortDir,
             @RequestParam(required = false) Integer page,
             @RequestParam(required = false) Integer size
             ){
        //q can be searched on title or code
        String search = q;
        if(search == null || search.isBlank()){
            if(code != null && !code.isBlank()) {
                search = code;
            }else if(title != null && !title.isBlank()){
                search = title;
            }
        }
        return courseService.searchCourses(search, active, sortBy, sortDir, page, size);
    }
    @PostMapping
    public ResponseEntity<Course> createCourse(@Valid @RequestBody CreateCourseRequest request) {

        Course created =
                courseService.createCourse(request.getTitle(),
                        request.getCode());

        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable String id, @Valid @RequestBody CreateCourseRequest request){
        return courseService.updateCourse(id, request.getTitle(), request.getCode());
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable String id){
        courseService.deleteCourseById(id);
    }
}
