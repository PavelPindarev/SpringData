import entities.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("soft_uni");
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();

//                 <property name="hibernate.show_sql" value="true"/>
//         за по-лесна проверка при отпечатване на резултата на конзолата сетни value = "false" във persistence.xml

        System.out.print("Hello which exercise do you want to check? ");
        int exerciseToCheck = Integer.parseInt(scanner.nextLine());
        switch (exerciseToCheck) {
            case 2:
                _2_ChangeCasing(manager);
                break;
            case 3:
                _3_ContainsEmployee(scanner, manager);
                break;
            case 4:
                _4_EmployeesWithSalaryOver50000(manager);
                break;
            case 5:
                _5_EmployeesFromDepartment(manager);
                break;
            case 6:
                _6_AddingANewAddressAndUpdatingEmployee(scanner, manager);
                break;
            case 7:
                _7_AddressesWithEmployeeCount(manager);
                break;
            case 8:
                _8_GetEmployeeWithProject(scanner, manager);
                break;
            case 9:
                _9_FindLatest10Projects(manager);
                break;
            case 10:
                _10_IncreaseSalaries(manager);
                break;
            case 11:
                _11_FindEmployeesByFirstName(scanner, manager);
                break;
            case 12:
                _12_EmployeesMaximumSalaries(manager);
                break;
            case 13:
                _13_RemoveTowns(scanner, manager);
                break;
            default:
                System.out.println("Invalid exercise!");
                break;
        }


        manager.getTransaction().commit();
        manager.close();
    }

    private static void _13_RemoveTowns(Scanner scanner, EntityManager manager) {
        String townToDelete = scanner.nextLine();

        List<Address> addresses = manager.createQuery("""
                        SELECT a
                        FROM Address a
                        WHERE a.town.name = :town
                        """, Address.class)
                .setParameter("town", townToDelete)
                .getResultList();

        List<Employee> employees = manager.createQuery("""
                        SELECT e
                        FROM Employee e
                        WHERE e.address.town.name = :town
                        """, Employee.class)
                .setParameter("town", townToDelete)
                .getResultList();

        for (Employee e : employees) {
            if (e.getAddress().getTown().getName().equals(townToDelete)) {
                e.setAddress(null);
            }
        }

        for (Address a : addresses) {
            if (a.getTown().getName().equals(townToDelete)) {
                manager.remove(a);
            }
        }

        String commandMessage = (addresses.size() > 1) ? "%d addresses in %s deleted" :
                "%d address in %s deleted";
        System.out.printf(commandMessage, addresses.size(), townToDelete);
    }

    private static void _12_EmployeesMaximumSalaries(EntityManager manager) {
        List<Department> departments = manager.createQuery(" SELECT d FROM Department d", Department.class)
                .getResultList();

        List<Employee> employees = departments.stream()
                .map(Department::getEmployees)
                .map(e -> e.stream()
                        .max(Comparator.comparingDouble(d -> d.getSalary().doubleValue()))
                        .orElseThrow(IllegalArgumentException::new))
                .filter(e -> e.getSalary().compareTo(new BigDecimal("30000")) < 0
                        || e.getSalary().compareTo(new BigDecimal("70000")) > 0)
                .collect(Collectors.toList());

        employees.forEach(employee ->
                System.out.printf("%s %.2f%n",
                        employee.getDepartment().getName(),
                        employee.getSalary())
        );
    }

    private static void _11_FindEmployeesByFirstName(Scanner scanner, EntityManager manager) {
        String startWith = scanner.nextLine();

        List<Employee> employees = manager.createQuery(""" 
                                SELECT e FROM Employee e
                                WHERE e.firstName LIKE :name
                                                """,
                        Employee.class)
                .setParameter("name", startWith + "%")
                .getResultList();

        employees.forEach(e ->
                System.out.printf("%s %s - %s - ($%.2f)%n",
                        e.getFirstName(),
                        e.getLastName(),
                        e.getDepartment().getName(),
                        e.getSalary())
        );
    }

    private static void _10_IncreaseSalaries(EntityManager manager) {
        List<Employee> employees = manager.createQuery("""
                        SELECT e FROM Employee e 
                        WHERE e.department.name IN(:E, :TD, :M, :IS)
                                """, Employee.class)
                .setParameter("E", "Engineering")
                .setParameter("TD", "Tool Design")
                .setParameter("M", "Marketing")
                .setParameter("IS", "Information Services")
                .getResultList();

        for (Employee e : employees) {
            e.setSalary(e.getSalary().multiply(new BigDecimal("1.12")));
            manager.persist(e);
            System.out.printf("%s %s ($%.2f)%n",
                    e.getFirstName(),
                    e.getLastName(),
                    e.getSalary());
        }
    }


    private static void _9_FindLatest10Projects(EntityManager manager) {
        Query query = manager.createQuery("""
                SELECT p FROM Project p
                ORDER BY p.name
                """);
        List<Project> projects = query.setMaxResults(10).getResultList();

        for (Project p : projects) {
            System.out.printf("""
                            Project name: %s
                             	Project Description: %s
                             	Project Start Date:%s
                             	Project End Date: %s
                            """,
                    p.getName(),
                    p.getDescription(),
                    dateFormat(p.getStartDate()),
                    p.getEndDate() == null ? "null" : dateFormat(p.getEndDate()));

        }
    }

    private static String dateFormat(LocalDateTime date) {
        Instant time = date.toInstant(ZoneOffset.ofHours(0));
        ZoneId timeZone = ZoneId.of("America/Argentina/Buenos_Aires");

        return DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss.S")
                .format(ZonedDateTime.ofInstant(time, timeZone));
    }

    private static void _8_GetEmployeeWithProject(Scanner scanner, EntityManager manager) {
        int id = Integer.parseInt(scanner.nextLine());
        Employee employee = manager.find(Employee.class, id);
        List<Project> projects = employee.getProjects()
                .stream().sorted(Comparator.comparing(Project::getName)).collect(Collectors.toList());

        StringBuilder builder = new StringBuilder(
                String.format("%s %s - %s\n",
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getJobTitle()));
        projects.forEach(project ->

                builder.append("    ").append(project.getName()).append(System.lineSeparator()));
        System.out.println(builder);
    }

    private static void _7_AddressesWithEmployeeCount(EntityManager manager) {
        Query query = manager.createQuery("SELECT a FROM Address a ORDER BY a.employees.size DESC ");
        query.setMaxResults(10);
        List<Address> resultList = query.getResultList();
        resultList.forEach(a -> System.out.printf("%s, %s - %d employees%n",
                a.getText(),
                a.getTown().getName(),
                a.getEmployees().size()
        ));
    }

    private static void _6_AddingANewAddressAndUpdatingEmployee(Scanner scanner, EntityManager manager) {
        String lastName = scanner.nextLine();
        Query query = manager.createQuery("SELECT e FROM Employee e ");
        List<Employee> resultList = query.getResultList();
        resultList = resultList.stream()
                .filter(employee -> employee.getLastName().equals(lastName))
                .collect(Collectors.toList());

        resultList.forEach(e -> {
            e.getAddress().setText("Vitoshka 15");
            manager.persist(e);
        });
    }

    private static void _5_EmployeesFromDepartment(EntityManager manager) {
        Query query = manager.createQuery("SELECT e FROM Employee e " +
                "WHERE e.department.id = 6 ORDER BY e.salary, e.id");
        List<Employee> resultList = query.getResultList();

        resultList.forEach(e -> System.out.printf("%s %s from %s - $%.2f%n",
                e.getFirstName(),
                e.getLastName(),
                e.getDepartment().getName(),
                e.getSalary()
        ));
    }

    private static void _4_EmployeesWithSalaryOver50000(EntityManager manager) {
        Query query = manager.createQuery("SELECT e FROM Employee e");
        List<Employee> resultList = query.getResultList();
        resultList.forEach(e -> {
            if (e.getSalary().intValue() > 50000) {
                System.out.println(e.getFirstName());
            }
        });
    }

    private static void _3_ContainsEmployee(Scanner scanner, EntityManager manager) {
        String nameToCheck = scanner.nextLine();
        Query query = manager.createQuery("SELECT CONCAT(e.firstName, ' ', e.lastName) FROM Employee e");
        List<String> resultList = query.getResultList();
        if (resultList.contains(nameToCheck)) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }

    private static void _2_ChangeCasing(EntityManager manager) {
        Query townsQuery = manager.createQuery("SELECT t From Town t");
        List<Town> towns = townsQuery.getResultList();

        towns.forEach(t -> {
            String name = t.getName();
            if (name.length() <= 5) {
                t.setName(name.toUpperCase());
                manager.persist(t);
            } else {
                manager.detach(t);
            }
            System.out.println(t);
        });
    }

}
