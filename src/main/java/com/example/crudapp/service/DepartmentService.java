package com.example.crudapp.service;

import com.example.crudapp.entity.Department;
import com.example.crudapp.model.department.DepartmentCreateRequest;
import com.example.crudapp.model.department.DepartmentCreateResponse;
import com.example.crudapp.model.department.DepartmentResponse;
import com.example.crudapp.model.department.DepartmentUpdateRequest;
import com.example.crudapp.repository.DepartmentRepository;
import com.example.crudapp.service.common.ServiceResult;
import com.example.crudapp.service.common.ServiceResultVoid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public ServiceResult<DepartmentCreateResponse> create(DepartmentCreateRequest request){
        if(request.getName() == null || request.getName().trim().isEmpty()){
            return ServiceResult.fail("Department name not empty.", HttpStatus.BAD_REQUEST );
        }

        Department department = new Department();
        department.setName(request.getName());

        Department saved = departmentRepository.save(department);

        return ServiceResult.success(new DepartmentCreateResponse(saved.getId()), HttpStatus.CREATED);
    }

    public ServiceResult<DepartmentResponse> getById(Long id){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);

        if(optionalDepartment.isEmpty()){
            return ServiceResult.fail("Department not found.", HttpStatus.NOT_FOUND);
        }

        Department department = optionalDepartment.get();
        DepartmentResponse response = new DepartmentResponse(department.getId(), department.getName());

        return ServiceResult.success(response);
    }

    public ServiceResult<List<DepartmentResponse>> getAll(){
        List<Department> departments = departmentRepository.findAll();

        List<DepartmentResponse> responses = departments.stream()
                .map(d -> new DepartmentResponse(d.getId(), d.getName()))
                .toList();

        return ServiceResult.success(responses);
    }

    public ServiceResultVoid update(Long id, DepartmentUpdateRequest request){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);

        if(optionalDepartment.isEmpty()){
            return ServiceResultVoid.fail("Department not found for updating.", HttpStatus.NOT_FOUND);
        }

        if(request.getName() == null || request.getName().trim().isEmpty()){
            return ServiceResultVoid.fail("Department name not empty", HttpStatus.BAD_REQUEST);
        }

        Department department = optionalDepartment.get();
        department.setName(request.getName());
        departmentRepository.save(department);

        return ServiceResultVoid.success(HttpStatus.NO_CONTENT);
    }

    public ServiceResultVoid delete(Long id){
        if(!departmentRepository.existsById(id)){
            return ServiceResultVoid.fail("Department not found for deleting.", HttpStatus.NOT_FOUND);
        }

        departmentRepository.deleteById(id);

        return ServiceResultVoid.success(HttpStatus.NO_CONTENT);
    }
}