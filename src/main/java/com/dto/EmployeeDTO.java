package com.dto;


import com.model.Employee;

public class EmployeeDTO {
    private final Long id;
    private final String name;
    private final String gender;

    public String getGender() {
        return gender;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getCompanyId() {
        return companyId;
    }

    private final Long companyId;

    public EmployeeDTO(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.gender = employee.getGender();
        this.companyId = employee.getCompany().getId();
    }
}
