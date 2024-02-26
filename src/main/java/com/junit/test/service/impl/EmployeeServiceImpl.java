package com.junit.test.service.impl;

import com.junit.test.exception.ResourseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.junit.test.model.Employee;
import com.junit.test.repository.EmployeeRepo;
import com.junit.test.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {


	@Autowired
	private EmployeeRepo employeeRepo;

	public EmployeeServiceImpl(EmployeeRepo employeeRepo){
		this.employeeRepo=employeeRepo;
	}

	@Override
	public Employee saveEmployee(Employee employee) {


		Optional<Employee> repoByEmail = employeeRepo.findByEmail(employee.getEmail());

		if (repoByEmail.isPresent()){
			throw new ResourseNotFoundException("The Given Employee is Already present with given email Id :" + employee.getEmail());
		}

		return employeeRepo.save(employee);
	}

	@Override
	public List<Employee> getAllEmployee() {
		return employeeRepo.findAll();
	}

	@Override
	public Employee getEmployeeById(int id) {
		return employeeRepo.findById(id).get();
	}

	@Override
	public Employee updateEmployee(Employee employee) {

//		Employee emp1= Employee.builder()
//				.id(employee.getId())
//				.firstName(employee.getFirstName())
//				.lastName(employee.getLastName())
//				.email(employee.getEmail())
//				.build();

				return employeeRepo.save(employee);


	}

	@Override
	public void deleteEmployee(int id) {

		employeeRepo.deleteById(id);

	}

}
