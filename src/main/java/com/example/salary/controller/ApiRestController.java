package com.example.salary.controller;

import com.example.salary.model.Employee;
import com.example.salary.model.EmployeeRequest;
import com.example.salary.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class ApiRestController {
    private final AccountService accountService;

    public ApiRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Set<Employee>> distributeSalary(@RequestBody EmployeeRequest request) {
        Set<Employee> employees = accountService.distributeSalary(request.getTeam(), request.getTotalAmount());
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
}
