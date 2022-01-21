package com.example.External.Rest.api;

import org.springframework.stereotype.Service;

@Service
public class EmployeeService {


//    public List<Employee> getall(){
//        Arrays.asList()
//    }

    public void save(Employee employee){
        Employee employee1 = new Employee();
        employee1.setEmployeeName(employee.getEmployeeName());
        employee1.setEmployeeRole(employee.getEmployeeRole());
        employee1.setEmployeeLocation(employee.getEmployeeLocation());
        employee1.setEmployeeNumber(employee.getEmployeeNumber());
    }
}
