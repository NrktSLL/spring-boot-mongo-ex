package com.nrkt.springbootmongoex.helper;

import com.nrkt.springbootmongoex.controller.web.WebController;
import com.nrkt.springbootmongoex.domain.Employee;
import com.nrkt.springbootmongoex.dto.request.EmployeeRequest;
import com.nrkt.springbootmongoex.dto.response.EmployeeResponse;
import com.nrkt.springbootmongoex.mapper.EmployeeMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeModelAssembler extends RepresentationModelAssemblerSupport<Employee, EmployeeResponse> {

    private final EmployeeMapper employeeMapper;

    public EmployeeModelAssembler(EmployeeMapper employeeMapper) {
        super(WebController.class, EmployeeResponse.class);
        this.employeeMapper = employeeMapper;
    }

    @Override
    @NonNull
    public EmployeeResponse toModel(@NonNull Employee entity) {
        return employeeMapper.employeeEntityToEmployeeResponse(entity);
    }

    public Employee toEmployeeEntity(EmployeeRequest employeeRequest) {
        return employeeMapper.employeeRequestToEmployeeEntity(employeeRequest);
    }

    public List<EmployeeResponse> toEmployeeResponseList(List<Employee> employeeList) {
        return employeeMapper.employeeEntityToEmployeeResponseList(employeeList);
    }
}
