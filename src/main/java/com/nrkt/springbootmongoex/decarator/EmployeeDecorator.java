package com.nrkt.springbootmongoex.decarator;

import com.nrkt.springbootmongoex.controller.rest.EmployeeController;
import com.nrkt.springbootmongoex.domain.Employee;
import com.nrkt.springbootmongoex.dto.response.EmployeeResponse;
import com.nrkt.springbootmongoex.mapper.EmployeeMapper;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public abstract class EmployeeDecorator implements EmployeeMapper {

    @Setter(onMethod = @__({@Autowired}))
    private EmployeeMapper employeeMapper;

    @Override
    public EmployeeResponse employeeEntityToEmployeeResponse(Employee employee) {
        EmployeeResponse employeeResponse = employeeMapper.employeeEntityToEmployeeResponse(employee);

        var requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

            if (request.getParameter("mediaType").equals("hal")) {

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
                                .withDeprecation("Get Employee"),
                        linkTo(methodOn(EmployeeController.class).getEmployeeByMail(employee.getEmail()))
                                .withRel("employee")
                                .withDeprecation("Get Employee By Email")
                };
                employeeResponse.add(links);
            }
        }
        return employeeResponse;
    }
}