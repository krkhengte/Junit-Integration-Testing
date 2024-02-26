package com.junit.test.service;

import com.junit.test.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

	public Employee saveEmployee(Employee employee);

	public List<Employee> getAllEmployee();

	public Employee getEmployeeById(int id);


	public Employee updateEmployee(Employee employee);

	public void deleteEmployee(int id);
	
}
