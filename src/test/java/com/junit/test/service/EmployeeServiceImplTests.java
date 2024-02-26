package com.junit.test.service;

import com.junit.test.exception.ResourseNotFoundException;
import com.junit.test.model.Employee;
import com.junit.test.repository.EmployeeRepo;
import com.junit.test.service.impl.EmployeeServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTests {

    @Mock
    private EmployeeRepo employeeRepo;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    Employee employee;
    @BeforeEach
    public void setup(){

       // employeeRepo= Mockito.mock(EmployeeRepo.class);
       // employeeService=new EmployeeServiceImpl(employeeRepo);
        employee=Employee.builder()
                .id(1)
                .firstName("Kartik")
                .lastName("Khengte")
                .email("krkhengte123@gmail.com")
                .build();


    }


// Junit Test for save employee object

    @DisplayName("Junit Test for save employee object")
    @Test
    public void givenEmployeeObject_whenSave_thenEmployeeObject(){

        // given - precondition or setup

         employee=Employee.builder()
                .id(1)
                .firstName("Kartik")
                .lastName("Khengte")
                .email("krkhengte123@gmail.com")
                .build();

        // Stubbing

        given(employeeRepo.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());

        given(employeeRepo.save(employee)).willReturn(employee);

        // when - action or the behaviour that we are going to perform

        Employee employee1 = employeeService.saveEmployee(employee);

        // then - verify the output

        assertThat(employee1).isNotNull();

    }

    // Junit Test for throw exception

    @DisplayName("Junit Test for Throws the exception")
    @Test
    public void givenExistingEmail_whenSave_thenThrowsException(){

        // given - precondition or setup

        given(employeeRepo.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));


        // when - action or the behaviour that we are going to perform
        Assertions.assertThrows(ResourseNotFoundException.class,()->{
            employeeService.saveEmployee(employee);
        });


        // then - verify the output

        verify(employeeRepo,never()).save(any(Employee.class));

    }



// Junit Test for getAllEmployee

    @DisplayName("Junit Test for getAllEmployee")
    @Test
    public void givenEmployeeList_whenFindAll_thenListOfEmployee(){

        // given - precondition or setup

       Employee employee1=Employee.builder()
                .id(1)
                .firstName("Vaibhav")
                .lastName("Khengte")
                .email("vaibhav@gmail.com")
                .build();

        given(employeeRepo.findAll()).willReturn(List.of(employee,employee1));


        // when - action or the behaviour that we are going to perform

        List<Employee> allEmployee = employeeService.getAllEmployee();


        // then - verify the output

        assertThat(allEmployee).isNotNull();
        assertThat(allEmployee.size()).isEqualTo(2);

    }


// Junit Test for get Employee by id

    @DisplayName("Junit Test for get Employee by id")
    @Test
    public void givenEmployeeId_whenFindById_thenEmployeeObject(){

        // given - precondition or setup

        given(employeeRepo.findById(employee.getId())).willReturn(Optional.of(employee));


        // when - action or the behaviour that we are going to perform

        Employee employeeById = employeeService.getEmployeeById(employee.getId());

        // then - verify the output

        assertThat(employeeById).isNotNull();
    }



// Junit Test for update employee object

    @DisplayName("Junit Test for update employee object")
    @Test
    public void givenEmployeeObject_whenSave_thenUpdatedEmployee(){

        // given - precondition or setup

        employee.setEmail("sangam@gmail.com");
        employee.setFirstName("Sangam");
        employee.setLastName("Dongre");

        given(employeeRepo.save(employee)).willReturn(employee);

        // when - action or the behaviour that we are going to perform

        Employee employee1 = employeeService.updateEmployee(employee);


        // then - verify the output

        assertThat(employee1).isNotNull();
        assertThat(employee1.getEmail().equals(employee.getEmail()));
    }


// Junit Test for delete employee

    @DisplayName("Junit Test for delete employee")
    @Test
    public void givenEMployeeId_whenDeleteById_thenDeleteEmployee(){

        // given - precondition or setup

        BDDMockito.willDoNothing().given(employeeRepo).deleteById(employee.getId());


        // when - action or the behaviour that we are going to perform

        employeeService.deleteEmployee(employee.getId());

        // then - verify the output

        verify(employeeRepo,times(1)).deleteById(employee.getId());

    }

}
