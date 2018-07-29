package com.responsity;

import com.model.Employee;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TestEntityManager testEntityManager;


    /**
     * 增加一个employee
     */
    @Test
    public void should_return_employee() {
        Employee saveEmployee = testEntityManager.persistFlushFind(new Employee("TT"));
        Employee employee = employeeRepository.save(new Employee("TT"));
        Assertions.assertThat(employee.getName()).isEqualTo(saveEmployee.getName());

    }


    /**
     * 获取employee列表
     */
    @Test
    public void should_return_employeeList() {
        Employee employee_1 = new Employee("employee-1");
        testEntityManager.persistFlushFind(employee_1);
        List<Employee> all = employeeRepository.findAll();
        Assertions.assertThat(all.size()).isEqualTo(1);
        Assertions.assertThat(all.get(0).getName()).isEqualTo(employee_1.getName());

    }


    /**
     * 获取某个具体employee
     */
    @Test
    public void should_return_employee_when_input_id() {
        Employee employee_1 = new Employee("employee-1");
        Employee saveEmployee = testEntityManager.persistFlushFind(employee_1);
        Employee employee = employeeRepository.findById(saveEmployee.getId()).get();
        Assertions.assertThat(employee.getId()).isEqualTo(saveEmployee.getId());
        Assertions.assertThat(employee.getName()).isEqualTo(saveEmployee.getName());

    }


    /**
     * 分页查询，page等于2，size等于1
     */
    @Test
    public void should_return_employeeList_when_input_page_and_pageSize() {
        Employee employee_1 = new Employee("employee-1");
        Employee employee_2 = new Employee("employee-2");
        testEntityManager.persistAndFlush(employee_1);
        Employee saveEmployee_2 = testEntityManager.persistAndFlush(employee_2);
        Page<Employee> employeeList = employeeRepository.findAll(new PageRequest(1, 1));
        Assertions.assertThat(employeeList.getContent().size()).isEqualTo(1);
        Assertions.assertThat(employeeList.getContent().get(0).getId()).isEqualTo(saveEmployee_2.getId());
        Assertions.assertThat(employeeList.getContent().get(0).getName()).isEqualTo(saveEmployee_2.getName());
    }


    /**
     * 筛选出所有男性Employee
     */
    @Test
    public void should_return_employeeList_when_input_select_by_gender_is_male() {
        Employee employee_1 = new Employee("employee-1","female");
        Employee employee_2 = new Employee("employee-2","male");
        testEntityManager.persistAndFlush(employee_1);
        Employee saveEmployee_2 = testEntityManager.persistAndFlush(employee_2);
        List<Employee> employeeList = employeeRepository.findByGender("male");
        Assertions.assertThat(employeeList.size()).isEqualTo(1);
        Assertions.assertThat(employeeList.get(0).getId()).isEqualTo(saveEmployee_2.getId());
        Assertions.assertThat(employeeList.get(0).getName()).isEqualTo(saveEmployee_2.getName());
    }


    /**
     * 更新某个employee
     */
    @Test
    public void should_return_update_employee_when_input_employeeId_and_update_employee_info() {
        Employee employee_1 = new Employee("employee-1","female");
        Employee save_employee_1 = testEntityManager.persistAndFlush(employee_1);
        employee_1.setName("employee-2");
        testEntityManager.flush();
        Employee employee = testEntityManager.persistAndFlush(employee_1);
        Employee save = employeeRepository.save(employee_1);
        Employee refreshEmployee = testEntityManager.find(Employee.class, save_employee_1.getId());
        Assertions.assertThat(save.getName()).isEqualTo("employee-2");
        Assertions.assertThat(save.getGender()).isEqualTo("female");

    }



    /**
     * 删除某个employee
     */
    @Test
    public void should_return_delete_employee_info_when_delete_a_employee() {
        Employee employee_1 = new Employee("employee-1","female");
        Employee save_employee_1 = testEntityManager.persistAndFlush(employee_1);
        employeeRepository.delete(save_employee_1);
        testEntityManager.flush();
        Employee employee = testEntityManager.find(Employee.class,save_employee_1.getId());
        Assertions.assertThat(employee).isEqualTo(null);
    }


}