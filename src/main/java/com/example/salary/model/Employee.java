package com.example.salary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private long mitarbeiterId;
    private double salary;

    public Employee(long mitarbeiterId) {
        this.mitarbeiterId = mitarbeiterId;
    }
}
