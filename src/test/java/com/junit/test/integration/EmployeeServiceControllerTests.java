package com.junit.test.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.junit.test.model.Employee;
import com.junit.test.repository.EmployeeRepo;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeServiceControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup(){

       employeeRepo.deleteAll();
    }


// Integration Test for Save Employee

    @DisplayName("Integration Test for Save Employee")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenSavedEmployeeObject() throws Exception {

        // given - precondition or setup

        Employee employee=Employee.builder()
                .firstName("Kartik")
                .lastName("Khengte")
                .email("krkhengte123@gmail.com")
                .build();

        // when - action or the behaviour that we are going to perform

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/saveEmpl").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // then - verify the output

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email",CoreMatchers.is(employee.getEmail())));
    }


// Integration Test for getAllEMployee

    @DisplayName("Integration Test for get All Employee")
    @Test
    public void givenEmployeeList_whenFindAll_thenGetAllEmployeeList() throws Exception {

        // given - precondition or setup
        List<Employee> empList=new ArrayList<>();

        empList.add(Employee.builder()
                .firstName("Sangam")
                .lastName("Dongre")
                .email("sangam@gmail.com")
                .build());
        empList.add(Employee.builder()
                .firstName("Kartik")
                .lastName("Khengte")
                .email("kartik@gmail.com")
                .build());

        employeeRepo.saveAll(empList);

        // when - action or the behaviour that we are going to perform

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/getAllEmployee"));

        // then - verify the output

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.is(empList.size())));

//            assertThat(empList.size()).isEqualTo(2);



    }

    // Integration Test for get Employee by Id

    @DisplayName("Integration Test for get Employee by Id")
    @Test
    public void givenEmployeeId_whenFindById_thenEmployeeObject() throws Exception {

        // given - precondition or setup

        Employee employee=Employee.builder()
                .firstName("Kartik")
                .lastName("Khengte")
                .email("kartik@gmail.com")
                .build();
        employeeRepo.save(employee);

        // when - action or the behaviour that we are going to perform

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/getEmployeeById/{id}", employee.getId()));


        // then - verify the output

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email",CoreMatchers.is(employee.getEmail())));

    }

    // Integration Test for update employee

    @DisplayName("Integration Test for update employee")
    @Test
    public void givenEmployeeObject_whenSave_thenUpdatedEmployee() throws Exception {

        // given - precondition or setup

        Employee employee=Employee.builder()
                .firstName("Kartik")
                .lastName("Khengte")
                .email("kartik@gmail.com")
                .build();
        employeeRepo.save(employee);

        employee.setEmail("krkhengte123@gmail.com");
        employeeRepo.save(employee);


        // when - action or the behaviour that we are going to perform

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/updateEmployee").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));


        // then - verify the output

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email",CoreMatchers.is(employee.getEmail())));

    }

    // Integration Test for delete employee

    @DisplayName("Integration Test for delete employee")
    @Test
    public void givenEmployeeId_whenDeleteById_thenRecordDeleted() throws Exception {

        // given - precondition or setup

        Employee employee=Employee.builder()
                .firstName("Kartik")
                .lastName("Khengte")
                .email("kartik@gmail.com")
                .build();
        employeeRepo.save(employee);

        // when - action or the behaviour that we are going to perform

        ResultActions response=mockMvc.perform(MockMvcRequestBuilders.delete("/deleteEmployee/{id}",employee.getId()));

        // then - verify the output

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

}
