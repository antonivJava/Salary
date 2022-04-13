package com.example.salary.service;

import com.example.salary.SalaryConfiguration;
import com.example.salary.config.SalaryConfig;
import com.example.salary.model.Employee;
import com.example.salary.model.EmployeeWorkingHours;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SalaryConfiguration.class)
class AccountServiceTest {

    @Autowired
    @MockBean
    SalaryConfig props;

    @Autowired
    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    @MockBean
    private AccountService accountService;

    @BeforeEach
    public void init() {
        accountService = new AccountService(props, restTemplate);
    }

    @Test
    public void distributeSalary() {
        EmployeeWorkingHours[] employeesWorkingHours = new EmployeeWorkingHours[3];
        employeesWorkingHours[0] = new EmployeeWorkingHours(1, 1, "2022-04-01", "2022-04-01T08:00", "2022-04-01T08:00", 200);
        employeesWorkingHours[1] = new EmployeeWorkingHours(2, 2, "2022-04-01", "2022-04-01T08:00", "2022-04-01T08:00", 300);
        employeesWorkingHours[2] = new EmployeeWorkingHours(3, 3, "2022-04-01", "2022-04-01T08:00", "2022-04-01T08:00", 500);

        HashSet<Employee> teamEmployees = new HashSet<>();
        teamEmployees.add(new Employee(1));
        teamEmployees.add(new Employee(2));

        when(props.getWorkingHoursUrl()).thenReturn("http://127.0.0.1");
        when(restTemplate.getForObject(eq("http://127.0.0.1"), any())).thenReturn(employeesWorkingHours);

        Set<Employee> distributedSalary = accountService.distributeSalary(teamEmployees, 100);

        Assertions.assertEquals(teamEmployees, distributedSalary);
    }
}

