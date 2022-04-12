package com.example.springmodelmapper.sevice;

import com.example.springmodelmapper.entity.Employee;
import com.example.springmodelmapper.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public List<Employee> selectEmployeesBornBefore(int year) {
        LocalDate date = LocalDate.of(year, 1, 1);
        return employeeRepository.findEmployeesByBirthdayBefore(date);
    }
}
