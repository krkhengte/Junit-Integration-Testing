package com.junit.test.controller;

import com.junit.test.model.Employee;
import com.junit.test.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeServiceController {

    @Autowired
    private EmployeeService employeeService;

//    public EmployeeServiceController(EmployeeService employeeService) {
//        this.employeeService = employeeService;
//    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/saveEmpl")
    public Employee saveEmployee(@RequestBody Employee employee){

        return employeeService.saveEmployee(employee);
    }

    @GetMapping("/getAllEmployee")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllEmployee(){

        return employeeService.getAllEmployee();
    }

    @GetMapping("/getEmployeeById/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getEmployeeById(@PathVariable int id){

        return employeeService.getEmployeeById(id);

    }

    @PutMapping("/updateEmployee")
    @ResponseStatus(HttpStatus.OK)
    public Employee updateEmployee(@RequestBody Employee employee){
        return employeeService.updateEmployee(employee);
    }


    @DeleteMapping("/deleteEmployee/{id}")
    public void deleteEmployee(@PathVariable int id){

        employeeService.deleteEmployee(id);
    }
}
