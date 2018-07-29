package com.controller;

import com.model.Employee;
import com.responsity.EmployeeRepository;
import com.service.EmployeeService;
import com.util.TypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {



    @Autowired
    private EmployeeService employeeService;

    @GetMapping("")
    @ResponseBody
    public List<Employee> findAllEmployee(Pageable pageable) {
        return employeeService.findAllEmployee(pageable);
    }


    @GetMapping("/id={param}")
    @ResponseBody
    public Employee findOneOfEmployee(@PathVariable Long param) {

            return employeeService.findOneOfEmployee(param);


    }



    @GetMapping("/gender={param}")
    @ResponseBody
    public List<Employee> findOneOfEmployee(@PathVariable String param) {
        return employeeService.findEmployeeByGender(param);
    }


//    @GetMapping("")
//    @ResponseBody
//    public List<Employee> selectEmployeeByPageAndPageSize(Pageable pageable) {
//
//        return employeeService.getEmployeeByPageAndpageSize(pageable);
//    }



    @PostMapping("")
    @ResponseBody
    public Employee addEmployee(@RequestBody Employee employee){
        Employee newEmployee = employeeService.addEmployee(employee);
        return newEmployee;
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public List<Employee> deleteEmployee(@PathVariable Long id ){
        return employeeService.deleteEmployee(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public List<Employee> updateEmployee(@PathVariable Long id,@RequestBody Employee employee){
        return employeeService.updateEmployee(id,employee);
    }






}