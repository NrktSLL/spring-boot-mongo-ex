package com.nrkt.springbootmongoex.service.employee;

import com.nrkt.springbootmongoex.domain.Employee;
import com.nrkt.springbootmongoex.dto.request.EmployeeRequest;
import com.nrkt.springbootmongoex.dto.response.EmployeeResponse;
import com.nrkt.springbootmongoex.exception.BadRequestException;
import com.nrkt.springbootmongoex.helper.EmployeeModelAssembler;
import com.nrkt.springbootmongoex.repository.EmployeeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    EmployeeRepository employeeRepository;

    EmployeeModelAssembler employeeModelAssembler;
    PagedResourcesAssembler<Employee> pagedResourcesAssembler;

    @Override
    public EmployeeResponse addEmployee(EmployeeRequest employee) {
        Employee newEmployee = employeeModelAssembler.toEmployeeEntity(employee);
        employeeRepository.save(newEmployee);

        return employeeModelAssembler.toModel(newEmployee);
    }

    @Override
    public List<EmployeeResponse> addEmployees(List<EmployeeRequest> employees) {
        List<Employee> employeeList = employees
                .stream()
                .map(employeeModelAssembler::toEmployeeEntity)
                .collect(Collectors.toList());
        employeeList = employeeRepository.saveAll(employeeList);

        return employeeModelAssembler.toEmployeeResponseList(employeeList);
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

        return employeeModelAssembler.toModel(existEmployee);
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

        return employeeModelAssembler.toModel(employee);
    }

    @Override
    public PagedModel<EmployeeResponse> getAllEmployee(Pageable pageable) {
        var employeePage = employeeRepository.findAll(pageable);

        return pagedResourcesAssembler.toModel(employeePage, employeeModelAssembler);
    }

    @Override
    public EmployeeResponse findEmployeeByEmail(String email) {
        var employee = employeeRepository
                .findByEmail(email)
                .orElseThrow(() -> new BadRequestException("employee Not Found"));

        return employeeModelAssembler.toModel(employee);
    }
}
