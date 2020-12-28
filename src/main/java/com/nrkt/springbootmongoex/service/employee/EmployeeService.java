package com.nrkt.springbootmongoex.service.employee;

import com.nrkt.springbootmongoex.dto.request.EmployeeRequest;
import com.nrkt.springbootmongoex.dto.response.EmployeeResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

public interface EmployeeService {

    EmployeeResponse addEmployee(EmployeeRequest employee);

    EmployeeResponse updateEmployee(String id, EmployeeRequest employee);

    void removeEmployee(String id);

    EmployeeResponse getEmployee(String id);

    PagedModel<EntityModel<EmployeeResponse>> getAllEmployee(Pageable pageable);
}
