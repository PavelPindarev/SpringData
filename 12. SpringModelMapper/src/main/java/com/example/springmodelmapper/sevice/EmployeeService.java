package com.example.springmodelmapper.sevice;

import com.example.springmodelmapper.entity.Employee;

import java.util.List;

public interface EmployeeService {
    void save(Employee employee);

    List<Employee> selectEmployeesBornBefore(int year);
}
