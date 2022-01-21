package com.example.External.Rest.api;

public class Employee {

    private String EmployeeName;
    private int EmployeeNumber;
    private String EmployeeRole;
    private String EmployeeLocation;

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public int getEmployeeNumber() {
        return EmployeeNumber;
    }

    public void setEmployeeNumber(int employeeNumber) {
        EmployeeNumber = employeeNumber;
    }

    public String getEmployeeRole() {
        return EmployeeRole;
    }

    public void setEmployeeRole(String employeeRole) {
        EmployeeRole = employeeRole;
    }

    public String getEmployeeLocation() {
        return EmployeeLocation;
    }

    public void setEmployeeLocation(String employeeLocation) {
        EmployeeLocation = employeeLocation;
    }
}
