package com.nrkt.springbootmongoex.repository;

import com.nrkt.springbootmongoex.domain.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EmployeeRepository extends MongoRepository<Employee,String> {
    Optional<Employee> findByEmail(String email);
}
