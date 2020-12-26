package com.nrkt.springbootmongoex.service.employee;


import com.nrkt.springbootmongoex.dto.request.EmployeeRequest;
import com.nrkt.springbootmongoex.dto.response.EmployeeResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse addEmployee(EmployeeRequest employee);

    List<EmployeeResponse> addEmployees(List<EmployeeRequest> employees);

    EmployeeResponse updateEmployee(String id, EmployeeRequest employee);

    void removeEmployee(String id);

    EmployeeResponse getEmployee(String id);

    PagedModel<EmployeeResponse> getAllEmployee(Pageable pageable);

    EmployeeResponse findEmployeeByEmail(String email);
}
