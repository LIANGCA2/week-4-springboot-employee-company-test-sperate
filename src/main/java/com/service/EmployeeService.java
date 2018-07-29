package com.service;


import com.model.Employee;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface EmployeeService {

    List<Employee> findAllEmployee(Pageable pageable);

    Employee addEmployee(Employee employee);

    List<Employee> deleteEmployee(Long id);

    List<Employee> updateEmployee(Long id, Employee employee);

    Employee findOneOfEmployee(Long id);

    List<Employee> findEmployeeByGender(String gender);

   // List<Employee> getEmployeeByPageAndpageSize(Pageable pageable);

}
