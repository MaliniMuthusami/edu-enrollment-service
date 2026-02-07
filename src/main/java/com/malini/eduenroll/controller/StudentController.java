package com.malini.eduenroll.controller;


import com.malini.eduenroll.dto.CreateStudentRequest;
import com.malini.eduenroll.dto.PaginatedResponse;
import com.malini.eduenroll.model.Student;
import com.malini.eduenroll.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * List students
 * GET /students
 * Get a student by id
 * GET /students/{id}
 * Create a student
 * POST /students
 * How will the HTTP requests look?
 */
@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /*// GET /students?name=yirou
    // GET /students?email=malini@example.com
    // GET /students?active=true
    // Partial search: GET /students?name=yi&email=@gmail.com
    // Won't work in our current implementation
    // will search only by name, email is ignored
    // New query param (q):
    // GET /students?q=yirou&sortBy=email&sortDir=desc
    // GET /students?q=shalini@example.com  -> search ALL names and emails
    // Student with abcshalini@example.com will match query
    // GET /students?q=yajit@example.com*/
    @GetMapping()
    public PaginatedResponse<Student> listStudents(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDir,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        // q can be a search on name or email
        String search = q;
        if(search == null || search.isBlank()) {
            if(name != null && !name.isBlank()) {
                search = name;
            } else if(email != null && !email.isBlank()) {
                search = email;
            }
        }

        return studentService.searchStudents(active, search, sortBy, sortDir, page, size);
    }

    // /students/{id} -> /students/697e19effc75bdb921d47ee4
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable String id) {
        return studentService.getStudentById(id);
    }

    @PostMapping()
    public ResponseEntity<Student> createStudent(@Valid @RequestBody CreateStudentRequest request) {
        Student created = studentService.createStudent(request.getName(), request.getEmail());
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable String id, @Valid @RequestBody CreateStudentRequest request) {
        return studentService.updateStudent(id, request.getName(), request.getEmail());
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
    }
}
