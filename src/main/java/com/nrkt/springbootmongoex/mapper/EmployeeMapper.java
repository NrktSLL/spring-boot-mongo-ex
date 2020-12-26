package com.nrkt.springbootmongoex.mapper;

import com.nrkt.springbootmongoex.decarator.EmployeeDecorator;
import com.nrkt.springbootmongoex.domain.Employee;
import com.nrkt.springbootmongoex.dto.request.EmployeeRequest;
import com.nrkt.springbootmongoex.dto.response.EmployeeResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
@DecoratedWith(EmployeeDecorator.class)
public interface EmployeeMapper {

    EmployeeResponse employeeEntityToEmployeeResponse(Employee employee);

    @Mapping(target = "hiredDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    Employee employeeRequestToEmployeeEntity(EmployeeRequest employeeRequest);

    List<EmployeeResponse> employeeEntityToEmployeeResponseList(List<Employee> employees);
}
