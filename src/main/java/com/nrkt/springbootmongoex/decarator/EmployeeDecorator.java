package com.nrkt.springbootmongoex.decarator;

import com.nrkt.springbootmongoex.controller.rest.EmployeeController;
import com.nrkt.springbootmongoex.domain.Employee;
import com.nrkt.springbootmongoex.dto.response.EmployeeResponse;
import com.nrkt.springbootmongoex.helper.MediaTypeInfo;
import com.nrkt.springbootmongoex.mapper.EmployeeMapper;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public abstract class EmployeeDecorator implements EmployeeMapper {

    @Setter(onMethod = @__({@Autowired}))
    private EmployeeMapper employeeMapper;

    @Override
    public EmployeeResponse employeeEntityToEmployeeResponse(Employee employee) {
        EmployeeResponse employeeResponse = employeeMapper.employeeEntityToEmployeeResponse(employee);

        if (MediaTypeInfo.getCurrentMediaType().equals("hal")) {

            Link[] links = new Link[]{
                    linkTo(methodOn(EmployeeController.class).createEmployee(null))
                            .withRel("employee")
                            .withDeprecation("Add Employee"),
                    linkTo(methodOn(EmployeeController.class).editEmployee(employee.getId(), null))
                            .withRel("employee")
                            .withDeprecation("Edit Employee")
                            .expand(employee.getId()),
                    linkTo(methodOn(EmployeeController.class).getEmployee(employee.getId()))
                            .withRel("employee")
                            .withDeprecation("Get Employee")
            };
            employeeResponse.add(links);
        }
        return employeeResponse;
    }
}