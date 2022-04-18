package com.example.springmodelmapper.entity.DTO;

import java.util.Set;
import java.util.stream.Collectors;

public class ManagerDTO {

    private String firstName;
    private String lastName;
    private Set<EmployeeDTO> employees;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<EmployeeDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<EmployeeDTO> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return String.format("%s %s | Employees: %d%n%s",
                firstName,
                lastName,
                employees.size(),
                employees
                        .stream()
                        .map(e -> "   -" + e)
                        .collect(Collectors.joining("\n"))
        );
    }
}
