package com.nrkt.springbootmongoex.controller.rest;

import com.nrkt.springbootmongoex.domain.Employee;
import com.nrkt.springbootmongoex.dto.request.EmployeeRequest;
import com.nrkt.springbootmongoex.dto.response.EmployeeResponse;
import com.nrkt.springbootmongoex.enums.PageSort;
import com.nrkt.springbootmongoex.error.ApiError;
import com.nrkt.springbootmongoex.service.employee.EmployeeService;
import io.swagger.annotations.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/v1/employees", produces = {"application/json", "application/xml", "application/hal+json"})
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Api(tags = "Employee")
public class EmployeeController {

    EmployeeService employeeService;

    @GetMapping
    @ApiOperation("Get All Employee")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Done!", response = EmployeeResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error!", response = ApiError.class),
            @ApiResponse(code = 400, message = "Bad Request!", response = ApiError.class)
    })
    public PagedModel<EmployeeResponse> getEmployeeList(
            @ApiParam(value = "Page Number")
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @ApiParam(value = "Size")
            @RequestParam(defaultValue = "5", required = false) Integer size,
            @ApiParam(value = "Page Sorting Type", allowableValues = "ASC, DESC")
            @RequestParam(defaultValue = "ASC", required = false) PageSort pageShort) {

        Pageable sorted = pageShort.equals(PageSort.DESC) ?
                PageRequest.of(page, size, Sort.sort(Employee.class).descending()) :
                PageRequest.of(page, size, Sort.sort(Employee.class).ascending());

        return employeeService.getAllEmployee(sorted);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created!", response = EmployeeResponse.class),
            @ApiResponse(code = 404, message = "Not Found!", response = ApiError.class),
            @ApiResponse(code = 400, message = "Bad Request!", response = ApiError.class),
            @ApiResponse(code = 500, message = "Internal Server Error!", response = ApiError.class)
    })
    @ApiOperation("Add Employee")
    public EmployeeResponse createEmployee(
            @ApiParam(name = "Employee", value = "Employee Specifications", required = true)
            @NotNull @RequestBody @Valid EmployeeRequest employeeRequest) {

        return employeeService.addEmployee(employeeRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Edit Employee")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Done!", response = EmployeeResponse.class),
            @ApiResponse(code = 404, message = "Not Found!", response = ApiError.class),
            @ApiResponse(code = 400, message = "Bad Request!", response = ApiError.class),
            @ApiResponse(code = 500, message = "Internal Server Error!", response = ApiError.class),
            @ApiResponse(code = 406, message = "Not Acceptable !", response = ApiError.class)
    })
    public EmployeeResponse editEmployee(
            @PathVariable @NotNull String id,
            @NotNull @Valid EmployeeRequest request) {

        return employeeService.updateEmployee(id, request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Done!", response = EmployeeResponse.class),
            @ApiResponse(code = 404, message = "Not Found!", response = ApiError.class),
            @ApiResponse(code = 500, message = "Internal Server Error!", response = ApiError.class),
            @ApiResponse(code = 406, message = "Not Acceptable !", response = ApiError.class),
            @ApiResponse(code = 400, message = "Bad Request!", response = ApiError.class)
    })
    @ApiOperation("Get Employee")
    public EmployeeResponse getEmployee(@PathVariable @NotNull String id) {
        return employeeService.getEmployee(id);
    }

    @ApiOperation("Delete Employee")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not Found!"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 406, message = "Not Acceptable !", response = ApiError.class),
            @ApiResponse(code = 400, message = "Bad Request!", response = ApiError.class)
    })
    public void deleteEmployee(@PathVariable @NotNull String id) {
        employeeService.removeEmployee(id);
    }

    @GetMapping("/findbyemail/{mail}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Bring Employee Information by Mail")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Done!", response = EmployeeResponse.class),
            @ApiResponse(code = 404, message = "Not Found!", response = ApiError.class),
            @ApiResponse(code = 500, message = "Internal Server Error!", response = ApiError.class),
            @ApiResponse(code = 406, message = "Not Acceptable !", response = ApiError.class),
            @ApiResponse(code = 400, message = "Bad Request!", response = ApiError.class)
    })
    public EmployeeResponse getEmployeeByMail(@NotNull @PathVariable @Valid String mail) {
        return employeeService.findEmployeeByEmail(mail);
    }
}
