package com.example.salary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeWorkingHours {
    private long arbeitszeitId;
    private long mitarbeiterId;
    private String datum;
    private String beginn;
    private String ende;
    private double dauer;
}
