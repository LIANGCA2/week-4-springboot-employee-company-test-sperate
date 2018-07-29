package com.serviceImpl;


import com.model.Employee;
import com.responsity.CompanyRepository;
import com.responsity.EmployeeRepository;
import com.service.EmployeeService;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {


    @Autowired
    private EmployeeRepository employeeRepository;



    @Override
    public List<Employee> findAllEmployee(Pageable pageable) {
        return employeeRepository.findAll(pageable).getContent();
    }

    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> updateEmployee(Long id, Employee employee) {
        if(employee.getId()==null){
            employee.setId(id);
            employeeRepository.save(employee);
        }else{
            employeeRepository.updateEmployeeIdById(id,employee.getId());
            employeeRepository.save(employee);

        }
       return employeeRepository.findAll();
    }

    @Override
    public Employee findOneOfEmployee(Long id) {
       return employeeRepository.findById(id).get();
    }

    @Override
    public List<Employee> findEmployeeByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

//    @Override
//    public List<Employee> getEmployeeByPageAndpageSize(Pageable pageable) {
//        return employeeRepository.findAll(pageable).getContent();
//
//    }


}
