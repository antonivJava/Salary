package com.example.salary.service;

import com.example.salary.model.Employee;

import java.util.Set;

public interface IAccountService {

    Set<Employee> distributeSalary(Set<Employee> team, double totalAmount);
}
