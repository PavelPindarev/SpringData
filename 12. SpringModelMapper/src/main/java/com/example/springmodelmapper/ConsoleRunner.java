package com.example.springmodelmapper;


import com.example.springmodelmapper.entity.DTO.EmployeeDTO;
import com.example.springmodelmapper.entity.Employee;
import com.example.springmodelmapper.sevice.EmployeeService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final EmployeeService employeeService;

    @Autowired
    public ConsoleRunner(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) throws Exception {
        persist();
        List<Employee> employees = employeeService.selectEmployeesBornBefore(1990);
        ModelMapper mapper = new ModelMapper();

        List<EmployeeDTO> employeeDTOS = employees
                .stream()
                .map(e -> mapper.map(e, EmployeeDTO.class))
                .sorted(Comparator.comparing(EmployeeDTO::getSalary).reversed())
                .collect(Collectors.toList());


        employeeDTOS.forEach(System.out::println);
    }


    private void persist() {
        Employee manager = new Employee(
                "Mrs.",
                "Manager",
                BigDecimal.ONE,
                LocalDate.now(),
                null);

        Employee first = new Employee(
                "first",
                "last",
                BigDecimal.TEN,
                LocalDate.now(),
                manager);

        Employee second = new Employee(
                "second",
                "last",
                BigDecimal.TEN,
                LocalDate.now(),
                manager);

//        this.employeeService.save(first);
        this.employeeService.save(second);
    }

//    1
//          Address address = new Address("Kazanluk", "Bulgaria");
//
//        Employee employee = new Employee("Pavel", "Pindarev",
//                BigDecimal.TEN, LocalDate.of(2004, 7, 22), address, onHoliday);
//
//        ModelMapper mapper = new ModelMapper();
//        EmployeeDTO employeeDTO = mapper.map(employee, EmployeeDTO.class);
//        System.out.println(employeeDTO);

//    2
//        Address address = new Address("Kazanluk", "Bulgaria");
//
//        Employee manager = new Employee("Pavel", "Pindarev", BigDecimal.TEN,
//                LocalDate.of(2004, 7, 22), address, true);
//
//        Employee employee1 = new Employee("Gosho", "Ivanov", BigDecimal.ONE,
//                LocalDate.of(2005, 4, 5), address, true);
//        manager.addEmployee(employee1);
//
//        Employee employee2 = new Employee("Petur", "Vanchev", BigDecimal.ONE,
//                LocalDate.of(2007, 1, 15), address, false);
//        manager.addEmployee(employee2);
//
//        ModelMapper mapper = new ModelMapper();
//
//        ManagerDTO managerDTO = mapper.map(manager, ManagerDTO.class);
//        System.out.println(managerDTO);
}
