package com.junit.test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.junit.test.model.Employee;


public interface EmployeeRepo extends JpaRepository<Employee, Integer>{

	Optional<Employee> findByEmail(String email);


}
