package com.junit.test.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.matches;

import java.util.List;
import java.util.Optional;
import java.util.function.IntPredicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.junit.test.model.Employee;

@DataJpaTest
public class EmployeeRepoTests {

	@Autowired
	private EmployeeRepo employeeRepo;

	@BeforeEach
	public void setup(){

		Employee employee = Employee.builder()
				.firstName("Kartik")
				.lastName("Khengte")
				.email("kartik@gmail.com")
				.build();
	}

	@DisplayName("Junit Test case for Save Employee Object")
	@Test
	public void givenEmployeeObject_whenSaveEmployee_thenSavedEmployeeObject() {

		// given

		Employee employee = Employee.builder().firstName("Kartik").lastName("Khengte").email("kartik@gmail.com")
				.build();

		// when

		Employee saveEmployee = employeeRepo.save(employee);

		// then

		assertThat(saveEmployee).isNotNull();
		assertThat(saveEmployee.getId()).isGreaterThan(0);

	}

	@DisplayName("Junit Test case for getAllEmployee")
	@Test
	public void givenNothing_whenGetAllEmployee_thenEmployeeObjectList() {

		// given

		Employee employee1 = Employee.builder().firstName("Kartik").lastName("Khengte").email("kartik@gmail.com")
				.build();

		Employee employee2 = Employee.builder().firstName("Vaibhav").lastName("Khengte").email("vaibhav@gmail.com")
				.build();

		Employee saveEmployee1 = employeeRepo.save(employee1);
		Employee saveEmployee2 = employeeRepo.save(employee2);

		// when

		List<Employee> empList = employeeRepo.findAll();

		// then

		assertThat(empList).isNotNull();
		assertThat(empList).hasSize(2);
	}

	// Junit Test for get employee by email

	@DisplayName("Junit Test for get employee by email")
	@Test
	public void givenEmailId_whenFindByEmail_thenEmployeeObject() {

		// given - precondition or setup

		Employee employee1 = Employee.builder().firstName("Kartik").lastName("Khengte").email("kartik@gmail.com")
				.build();

		Employee saveEmployee1 = employeeRepo.save(employee1);

		// when - action or the behaviour that we are going to perform

		Employee emp1 = employeeRepo.findByEmail(employee1.getEmail()).get();

		// then - verify the output

		assertThat(emp1).isNotNull();
		assertThat(matches(employee1.getEmail()).equals(emp1.getEmail()));

	}
	

	// Junit Test for update employee object

	@DisplayName("Junit Test for update employee object")
	@Test
	public void givenEmployeeId_whenUpdateEmployee_thenUpdatedEmployeeObj(){

		// given - precondition or setup
		
		Employee employee1 = Employee.builder()
				.firstName("Kartik").lastName("Khengte").email("kartik@gmail.com")
				.build();

		Employee saveEmployee1 = employeeRepo.save(employee1);
		
		
		// when - action or the behaviour that we are going to perform
		
		Employee saveEmployee2 = employeeRepo.findById(employee1.getId()).get();
		saveEmployee2.setEmail("krkhengte123@gmail.com");
		Employee saveEmployee3 = employeeRepo.save(saveEmployee2);
		
		// then - verify the output

		assertThat(saveEmployee3).isNotNull();
		assertThat(saveEmployee3.getEmail().equals("krkhengte123@gmail.com"));
	}



// Junit Test for delete employee object

	@DisplayName("Junit Test for delete employee object")
	@Test
	public void givenEmployee_whenDeleteEmployee_thenEmptyEmployeeObject(){
		// given - precondition or setup

		Employee employee1 = Employee.builder()
				.firstName("Kartik").lastName("Khengte").email("kartik@gmail.com")
				.build();

		Employee saveEmployee1 = employeeRepo.save(employee1);

		// when - action or the behaviour that we are going to perform

		employeeRepo.delete(saveEmployee1);
		Optional<Employee> repoById = employeeRepo.findById(saveEmployee1.getId());

		// then - verify the output

		assertThat(repoById).isEmpty();

	}
}
