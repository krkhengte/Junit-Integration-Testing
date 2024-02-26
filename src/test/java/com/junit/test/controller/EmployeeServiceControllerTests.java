package com.junit.test.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.junit.test.model.Employee;
import com.junit.test.service.EmployeeService;
import static org.assertj.core.api.Assertions.assertThat;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import static org.mockito.BDDMockito.given;

import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest
public class EmployeeServiceControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee employee;
    @BeforeEach
    public void setup(){

         employee=Employee.builder()
                .id(1)
                .firstName("Kartik")
                .lastName("Khengte")
                .email("krkhengte123@gmail.com")
                .build();
    }

// Junit Test for save employee controller layer

    @DisplayName("Junit Test for save employee controller layer")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenSavedEmployeeObject() throws Exception {

        // given - precondition or setup



        // Stubbing

        given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // when - action or the behaviour that we are going to perform

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/saveEmpl").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));


        // then - verify the output


            response.andExpect(MockMvcResultMatchers.status().isCreated())
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",CoreMatchers.is(employee.getLastName())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.email",CoreMatchers.is(employee.getEmail())));

    }


// Junit Test for get All Employee

    @DisplayName("Junit Test for get All Employee")
    @Test
    public void givenEmployeeList_whenFindAll_thenGetAllEmployeeList() throws Exception {

        // given - precondition or setup
        List<Employee> empList=new ArrayList<>();

        empList.add(Employee.builder()
                .firstName("Sangam")
                .lastName("Dongre")
                .email("sangam@gmail.com")
                .build());
        empList.add(employee);

            given(employeeService.getAllEmployee()).willReturn(empList);


        // when - action or the behaviour that we are going to perform

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/getAllEmployee").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empList)));

        // then - verify the output

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.is(empList.size())));

//            assertThat(empList.size()).isEqualTo(2);



    }


// Junit Test for getEmployeeById

    @DisplayName("Junit Test for getEmployeeById")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {

        // given - precondition or setup

        given(employeeService.getEmployeeById(employee.getId())).willReturn(employee);


        // when - action or the behaviour that we are going to perform

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/getEmployeeById/{id}", employee.getId()));
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(employee)));


        // then - verify the output

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email",CoreMatchers.is(employee.getEmail())));


    }


// Junit Test for update employee

    @DisplayName("Junit Test for update employee")
    @Test
    public void givenEmployee_whenSave_thenUpdatedEmployee() throws Exception {

        // given - precondition or setup

            employee.setId(1);
            employee.setEmail("vaibhav@gmail.com");
            employee.setFirstName("Vaibhav");
            employee.setLastName("Khengte");

            given(employeeService.updateEmployee(employee)).willReturn(employee);

        // when - action or the behaviour that we are going to perform

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/updateEmployee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));


        // then - verify the output

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",CoreMatchers.is(employee.getFirstName())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",CoreMatchers.is(employee.getLastName())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.email",CoreMatchers.is(employee.getEmail())));
    }



// Junit Test for delete employee by id

    @DisplayName("Junit Test for delete employee by id")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenNothing() throws Exception {

        // given - precondition or setup

        BDDMockito.willDoNothing().given(employeeService).deleteEmployee(employee.getId());

        // when - action or the behaviour that we are going to perform

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/deleteEmployee/{id}", employee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));


        // then - verify the output

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}

