package com.malini.eduenroll.bootstrap;

import com.malini.eduenroll.model.Course;
import com.malini.eduenroll.model.Enrollment;
import com.malini.eduenroll.model.Student;
import com.malini.eduenroll.repo.CourseRepository;
import com.malini.eduenroll.repo.EnrollmentRepository;
import com.malini.eduenroll.repo.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;


@Component
public class DataSeeder implements CommandLineRunner {
    private final StudentRepository repo;
    private final CourseRepository courseRepo;
    private final EnrollmentRepository enrollRepo;

    public DataSeeder(StudentRepository repo, CourseRepository courseRepo,
                      EnrollmentRepository enrollRepo) {
        this.repo = repo;
        this.courseRepo = courseRepo;
        this.enrollRepo = enrollRepo;
    }

    @Override
    public void run(String... args) {
        // insert only if the count (number of documents in the collection) == 0
        if(repo.count() > 0) {
            return;
        }
        // need a list of students to save
        List<Student> students = List.of(
                new Student(null, "malini", "ma@example.com", true),
                new Student(null, "ashik", "as@example.com", true),
                new Student(null, "thirumalini", "t-malini@example.com", false),
                new Student(null, "shalini", "sh@example.com", true),
                new Student(null, "thirumani", "th@example.com", true),
                new Student(null, "yirou", "yi@example.com", false),
                new Student(null, "armaan", "ar@example.com", true),
                new Student(null, "ashwin", "ash@example.com", true)
        );
        System.out.println("Saving students: " + students);

        // repo save all the students
        repo.saveAll(students);

        if(courseRepo.count() > 0) {
            return;
        }
        // need a list of courses to save
        List<Course> courses = List.of(
               new Course(true, null, "Spring Boot Foundations", "SB101"),
                new Course(true, null, "Spring Boot Advanced", "SB201"),
                new Course(false, null, "Database", "DB200"),
                new Course(true, null, "Java Foundations", "J101"),
                new Course(false, null, "Data Structure Algorithm", "DSA")
        );
        System.out.println("Saving courses: " + courses);

        // repo save all the courses
        courseRepo.saveAll(courses);

        if(enrollRepo.count() > 0) {
            return;
        }
        // need a list of courses to save
        List<Enrollment> enrollments = List.of(
               new Enrollment(null, "6985feeb97155f6c395be143", "6985feec97155f6c395be14b", Instant.now(), "ACTIVE"),
                new Enrollment(null, "6985feeb97155f6c395be143", "6985feec97155f6c395be14c", Instant.now(), "CANCELLED"),
                new Enrollment(null, "6985feeb97155f6c395be146", "6985feec97155f6c395be14d", Instant.now(), "ACTIVE"),
                new Enrollment(null, "6985feeb97155f6c395be148", "6985feec97155f6c395be14e", Instant.now(), "CANCELLED"),
                new Enrollment(null, "6985feeb97155f6c395be145", "6985feec97155f6c395be14f", Instant.now(), "ACTIVE")
        );
        System.out.println("Saving enrollments: " + enrollments);

        // repo save all the enrollments
        enrollRepo.saveAll(enrollments);
    }
}
/*
repo.saveAll -> saves the list of students; id = null, means the MongoDB should generate the ObjectIds
DTO - HTTP request/response
*/
