package com.nrkt.springbootmongoex.service.employee;

import com.nrkt.springbootmongoex.domain.Employee;
import com.nrkt.springbootmongoex.dto.request.EmployeeRequest;
import com.nrkt.springbootmongoex.dto.response.EmployeeResponse;
import com.nrkt.springbootmongoex.exception.BadRequestException;
import com.nrkt.springbootmongoex.mapper.EmployeeMapper;
import com.nrkt.springbootmongoex.repository.EmployeeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    EmployeeRepository employeeRepository;
    EmployeeMapper employeeMapper;

    PagedResourcesAssembler<EmployeeResponse> pagedResourcesAssembler;

    @Override
    public EmployeeResponse addEmployee(EmployeeRequest employee) {
        Employee newEmployee = employeeMapper.employeeRequestToEmployeeEntity(employee);
        employeeRepository.save(newEmployee);

        return employeeMapper.employeeEntityToEmployeeResponse(newEmployee);
    }

    @Override
    public EmployeeResponse updateEmployee(String id, EmployeeRequest employee) {
        var existEmployee = employeeRepository
                .findById(id)
                .orElseThrow(() -> new BadRequestException("employee Not Found"));

        existEmployee = Employee.builder()
                .id(id)
                .firstName(employee.getFirstName())
                .secondName(employee.getSecondName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .hiredDate(new Date())
                .build();

        existEmployee = employeeRepository.save(existEmployee);

        return employeeMapper.employeeEntityToEmployeeResponse(existEmployee);
    }

    @Override
    public void removeEmployee(String id) {
        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(() -> new BadRequestException("employee Not Found"));

        employeeRepository.delete(employee);
    }

    @Override
    public EmployeeResponse getEmployee(String id) {
        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(() -> new BadRequestException("employee Not Found"));

        return employeeMapper.employeeEntityToEmployeeResponse(employee);
    }

    @Override
    public PagedModel<EntityModel<EmployeeResponse>> getAllEmployee(Pageable pageable) {
        var employeePage = employeeRepository.findAll(pageable);
        Page<EmployeeResponse> employeeResponses= employeePage.map(employeeMapper::employeeEntityToEmployeeResponse);

        return pagedResourcesAssembler.toModel(employeeResponses);
    }
}
