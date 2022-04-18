package com.example.springmodelmapper.repository;

import com.example.springmodelmapper.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findEmployeesByBirthdayBefore(LocalDate date);
}
