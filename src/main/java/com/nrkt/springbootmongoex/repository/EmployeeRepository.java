package com.nrkt.springbootmongoex.repository;

import com.nrkt.springbootmongoex.domain.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee,String> {
}
