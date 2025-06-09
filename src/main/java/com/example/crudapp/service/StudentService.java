package com.example.crudapp.service;

import com.example.crudapp.entity.Department;
import com.example.crudapp.entity.Student;
import com.example.crudapp.model.department.DepartmentResponse;
import com.example.crudapp.model.student.StudentCreateRequest;
import com.example.crudapp.model.student.StudentCreateResponse;
import com.example.crudapp.model.student.StudentResponse;
import com.example.crudapp.model.student.StudentUpdateRequest;
import com.example.crudapp.repository.StudentRepository;
import com.example.crudapp.service.common.ServiceResult;
import com.example.crudapp.service.common.ServiceResultVoid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final DepartmentService departmentService;

    public ServiceResult<StudentCreateResponse> create(StudentCreateRequest request) {
        if (request.getEmail() != null && studentRepository.existsByEmail(request.getEmail())) {
            return ServiceResult.fail("This email address already exists.", HttpStatus.CONFLICT);
        }

        ServiceResult<DepartmentResponse> departmentResponseResult = departmentService.getById(request.getDepartmentId());
        if (departmentResponseResult.isFail()) {
            return ServiceResult.fail("Please enter a valid department ID.", HttpStatus.NOT_FOUND);
        }

        // Manuel Department nesnesi
        Department department = new Department();
        department.setId(departmentResponseResult.getData().getId());
        department.setName(departmentResponseResult.getData().getName());

        Student student = new Student();
        student.setFullName(request.getFullName());
        student.setEmail(request.getEmail());
        student.setAge(request.getAge());
        student.setIsActive(request.getIsActive());
        student.setEnrollmentDate(request.getEnrollmentDate());
        student.setDepartment(department);

        Student saved = studentRepository.save(student);

        return ServiceResult.success(new StudentCreateResponse(saved.getId()), HttpStatus.CREATED);
    }

    public ServiceResult<StudentResponse> getById(Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);

        if (optionalStudent.isEmpty()) {
            return ServiceResult.fail("Student not found.", HttpStatus.NOT_FOUND);
        }

        Student student = optionalStudent.get();
        Department department = student.getDepartment();
        DepartmentResponse departmentResponse = new DepartmentResponse(department.getId(), department.getName());

        StudentResponse response = new StudentResponse(
                student.getId(),
                student.getFullName(),
                student.getEmail(),
                student.getAge(),
                student.getIsActive(),
                student.getEnrollmentDate(),
                departmentResponse
        );

        return ServiceResult.success(response);
    }

    public ServiceResult<List<StudentResponse>> getAll() {
        List<Student> students = studentRepository.findAll();

        List<StudentResponse> responses = students.stream().map(student -> {
                    Department department = student.getDepartment();
                    DepartmentResponse departmentResponse = new DepartmentResponse(department.getId(), department.getName());

                    return new StudentResponse(
                            student.getId(),
                            student.getFullName(),
                            student.getEmail(),
                            student.getAge(),
                            student.getIsActive(),
                            student.getEnrollmentDate(),
                            departmentResponse
                    );
                })
                .toList();

        return ServiceResult.success(responses);
    }

    public ServiceResultVoid update(Long id, StudentUpdateRequest request) {
        Optional<Student> optionalStudent = studentRepository.findById(id);

        if (optionalStudent.isEmpty()) {
            return ServiceResultVoid.fail("Student not found for updating.", HttpStatus.NOT_FOUND);
        }

        if (request.getEmail() != null && studentRepository.existsByEmail(request.getEmail())) {
            return ServiceResultVoid.fail("This email address already exists.", HttpStatus.CONFLICT);
        }

        ServiceResult<DepartmentResponse> departmentResult = departmentService.getById(request.getDepartmentId());
        if (departmentResult.isFail()) {
            return ServiceResultVoid.fail("Please enter a valid department ID.", HttpStatus.NOT_FOUND);
        }

        Department department = new Department();
        department.setId(request.getDepartmentId());
        department.setName(departmentResult.getData().getName());

        Student student = optionalStudent.get();
        student.setFullName(request.getFullName());
        student.setEmail(request.getEmail());
        student.setAge(request.getAge());
        student.setIsActive(request.getIsActive());
        student.setEnrollmentDate(request.getEnrollmentDate());
        student.setDepartment(department);

        studentRepository.save(student);
        return ServiceResultVoid.success(HttpStatus.NO_CONTENT);
    }

    public ServiceResultVoid delete(Long id) {
        if (!studentRepository.existsById(id)) {
            return ServiceResultVoid.fail("No students found to be deleted.", HttpStatus.NOT_FOUND);
        }

        studentRepository.deleteById(id);
        return ServiceResultVoid.success(HttpStatus.NO_CONTENT);
    }

    public ServiceResult<StudentResponse> getByEmail(String email) {
        Optional<Student> optionalStudent = studentRepository.findByEmail(email);

        if (optionalStudent.isEmpty()) {
            return ServiceResult.fail("Student not found with this email.", HttpStatus.NOT_FOUND);
        }

        Student student = optionalStudent.get();
        Department department = student.getDepartment();
        DepartmentResponse departmentResponse = new DepartmentResponse(department.getId(), department.getName());

        StudentResponse response = new StudentResponse(
                student.getId(),
                student.getFullName(),
                student.getEmail(),
                student.getAge(),
                student.getIsActive(),
                student.getEnrollmentDate(),
                departmentResponse
        );

        return ServiceResult.success(response);
    }

    public ServiceResult<List<StudentResponse>> getByFullName(String fullName) {
        List<Student> students = studentRepository.findByFullNameAndIsActiveTrue(fullName);

        if (students.isEmpty()) {
            return ServiceResult.fail("No active students found with this full name.", HttpStatus.NOT_FOUND);
        }

        List<StudentResponse> responses = students.stream().map(student -> {
            Department department = student.getDepartment();
            DepartmentResponse departmentResponse = new DepartmentResponse(department.getId(), department.getName());

            return new StudentResponse(
                    student.getId(),
                    student.getFullName(),
                    student.getEmail(),
                    student.getAge(),
                    student.getIsActive(),
                    student.getEnrollmentDate(),
                    departmentResponse
            );
        }).toList();

        return ServiceResult.success(responses);
    }

    public ServiceResult<List<StudentResponse>> getByAgeRange(int minAge, int maxAge) {
        List<Student> students = studentRepository.findByAgeBetween(minAge, maxAge);

        if (students.isEmpty()) {
            return ServiceResult.fail("No students found in this age range.", HttpStatus.NOT_FOUND);
        }

        List<StudentResponse> responses = students.stream().map(student -> {
            Department department = student.getDepartment();
            DepartmentResponse departmentResponse = new DepartmentResponse(department.getId(), department.getName());

            return new StudentResponse(
                    student.getId(),
                    student.getFullName(),
                    student.getEmail(),
                    student.getAge(),
                    student.getIsActive(),
                    student.getEnrollmentDate(),
                    departmentResponse
            );
        }).toList();

        return ServiceResult.success(responses);
    }

    public ServiceResult<List<StudentResponse>> getActiveStudentsOrderedByEnrollmentDate() {
        List<Student> students = studentRepository.findByIsActiveTrueOrderByEnrollmentDateDesc();

        if (students.isEmpty()) {
            return ServiceResult.fail("No active students found.", HttpStatus.NOT_FOUND);
        }

        List<StudentResponse> responses = students.stream().map(student -> {
            Department department = student.getDepartment();
            DepartmentResponse departmentResponse = new DepartmentResponse(department.getId(), department.getName());

            return new StudentResponse(
                    student.getId(),
                    student.getFullName(),
                    student.getEmail(),
                    student.getAge(),
                    student.getIsActive(),
                    student.getEnrollmentDate(),
                    departmentResponse
            );
        }).toList();

        return ServiceResult.success(responses);
    }

    public ServiceResult<StudentResponse> getFirstStudentByEnrollmentDate() {
        Student student = studentRepository.findFirstByOrderByEnrollmentDateAsc();

        if (student == null) {
            return ServiceResult.fail("No students found.", HttpStatus.NOT_FOUND);
        }

        Department department = student.getDepartment();
        DepartmentResponse departmentResponse = new DepartmentResponse(department.getId(), department.getName());

        StudentResponse response = new StudentResponse(
                student.getId(),
                student.getFullName(),
                student.getEmail(),
                student.getAge(),
                student.getIsActive(),
                student.getEnrollmentDate(),
                departmentResponse
        );

        return ServiceResult.success(response);
    }

    public ServiceResult<List<StudentResponse>> getTop3StudentsByAge(int age) {
        List<Student> students = studentRepository.findTop3ByAgeGreaterThanOrderByAgeDesc(age);

        if (students.isEmpty()) {
            return ServiceResult.fail("No students found older than the specified age.", HttpStatus.NOT_FOUND);
        }

        List<StudentResponse> responses = students.stream().map(student -> {
            Department department = student.getDepartment();
            DepartmentResponse departmentResponse = new DepartmentResponse(department.getId(), department.getName());

            return new StudentResponse(
                    student.getId(),
                    student.getFullName(),
                    student.getEmail(),
                    student.getAge(),
                    student.getIsActive(),
                    student.getEnrollmentDate(),
                    departmentResponse
            );
        }).toList();

        return ServiceResult.success(responses);
    }

    public ServiceResult<List<StudentResponse>> getByAges(List<Integer> ages) {
        List<Student> students = studentRepository.findByAgeIn(ages);

        if (students.isEmpty()) {
            return ServiceResult.fail("No students found with the specified ages.", HttpStatus.NOT_FOUND);
        }

        List<StudentResponse> responses = students.stream().map(student -> {
            Department department = student.getDepartment();
            DepartmentResponse departmentResponse = new DepartmentResponse(department.getId(), department.getName());

            return new StudentResponse(
                    student.getId(),
                    student.getFullName(),
                    student.getEmail(),
                    student.getAge(),
                    student.getIsActive(),
                    student.getEnrollmentDate(),
                    departmentResponse
            );
        }).toList();

        return ServiceResult.success(responses);
    }

    public ServiceResult<List<StudentResponse>> getStudentsWithNullEmail() {
        List<Student> students = studentRepository.findByEmailIsNull();

        if (students.isEmpty()) {
            return ServiceResult.fail("No students found with null email.", HttpStatus.NOT_FOUND);
        }

        List<StudentResponse> responses = students.stream().map(student -> {
            Department department = student.getDepartment();
            DepartmentResponse departmentResponse = new DepartmentResponse(department.getId(), department.getName());

            return new StudentResponse(
                    student.getId(),
                    student.getFullName(),
                    student.getEmail(),
                    student.getAge(),
                    student.getIsActive(),
                    student.getEnrollmentDate(),
                    departmentResponse
            );
        }).toList();

        return ServiceResult.success(responses);
    }

    public ServiceResult<List<StudentResponse>> getByDepartmentName(String departmentName) {
        List<Student> students = studentRepository.findByDepartment_Name(departmentName);

        if (students.isEmpty()) {
            return ServiceResult.fail("No students found in this department.", HttpStatus.NOT_FOUND);
        }

        List<StudentResponse> responses = students.stream().map(student -> {
            Department department = student.getDepartment();
            DepartmentResponse departmentResponse = new DepartmentResponse(department.getId(), department.getName());

            return new StudentResponse(
                    student.getId(),
                    student.getFullName(),
                    student.getEmail(),
                    student.getAge(),
                    student.getIsActive(),
                    student.getEnrollmentDate(),
                    departmentResponse
            );
        }).toList();

        return ServiceResult.success(responses);
    }

    public ServiceResult<List<StudentResponse>> getByEnrollmentDateBefore(LocalDate date) {
        List<Student> students = studentRepository.findByEnrollmentDateBefore(date);

        if (students.isEmpty()) {
            return ServiceResult.fail("No students found enrolled before the specified date.", HttpStatus.NOT_FOUND);
        }

        List<StudentResponse> responses = students.stream().map(student -> {
            Department department = student.getDepartment();
            DepartmentResponse departmentResponse = new DepartmentResponse(department.getId(), department.getName());

            return new StudentResponse(
                    student.getId(),
                    student.getFullName(),
                    student.getEmail(),
                    student.getAge(),
                    student.getIsActive(),
                    student.getEnrollmentDate(),
                    departmentResponse
            );
        }).toList();

        return ServiceResult.success(responses);
    }

    public ServiceResult<List<StudentResponse>> getActiveStudentsWithPagination(int page, int size) {
        Page<Student> studentPage = studentRepository.findByIsActiveTrue(Pageable.ofSize(size).withPage(page));

        if (studentPage.isEmpty()) {
            return ServiceResult.fail("No active students found.", HttpStatus.NOT_FOUND);
        }

        List<StudentResponse> responses = studentPage.stream().map(student -> {
            Department department = student.getDepartment();
            DepartmentResponse departmentResponse = new DepartmentResponse(department.getId(), department.getName());

            return new StudentResponse(
                    student.getId(),
                    student.getFullName(),
                    student.getEmail(),
                    student.getAge(),
                    student.getIsActive(),
                    student.getEnrollmentDate(),
                    departmentResponse
            );
        }).toList();

        return ServiceResult.success(responses);
    }
}
