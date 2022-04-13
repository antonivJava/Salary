package com.example.salary.service;

import com.example.salary.config.SalaryConfig;
import com.example.salary.model.Employee;
import com.example.salary.model.EmployeeWorkingHours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

@Service
public class AccountService implements IAccountService {
    private RestTemplate restTemplate;
    private final SalaryConfig props;

    public AccountService(SalaryConfig props, RestTemplate restTemplate) {
        this.props = props;
        this.restTemplate = restTemplate;
    }

    @Override
    public Set<Employee> distributeSalary(Set<Employee> teamEmployees, double totalAmount) {
        Set<EmployeeWorkingHours> employeesWorkingHours = new HashSet<>(Arrays.asList(
                restTemplate.getForObject(props.getWorkingHoursUrl(), EmployeeWorkingHours[].class)));

        Set<Long> teamEmployeeIds = teamEmployees.stream().map(Employee::getMitarbeiterId).collect(Collectors.toSet());

        Map<Long, Double> durationHoursByEmployeeId = employeesWorkingHours.stream()
                .filter(employeeWorkingHours -> teamEmployeeIds.contains(employeeWorkingHours.getMitarbeiterId()))
                .collect(groupingBy(
                        EmployeeWorkingHours::getMitarbeiterId,
                        summingDouble(EmployeeWorkingHours::getDauer)));

        double totalWorkingHours = durationHoursByEmployeeId.values().stream().mapToDouble(Double::doubleValue).sum();

        teamEmployees.stream().forEach(employee -> {
            double salary = durationHoursByEmployeeId.get(employee.getMitarbeiterId()) / totalWorkingHours * totalAmount;
            employee.setSalary(salary);
        });

        return teamEmployees;
    }
}
