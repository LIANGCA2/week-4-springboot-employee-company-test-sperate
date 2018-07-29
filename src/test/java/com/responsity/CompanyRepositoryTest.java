package com.responsity;

import com.model.Company;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DataJpaTest
public class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TestEntityManager testEntityManager;


    /**
     * 获取company列表
     */
    @Test
    public void should_return_companyList(){
        Company company_1 = new Company("company_1");
        Company save_company = testEntityManager.persistFlushFind(company_1);
        List<Company> all = companyRepository.findAll();
        Assertions.assertThat(all.size()).isEqualTo(1);
        Assertions.assertThat(all.get(0).getName()).isEqualTo(save_company.getName());

    }


    /**
     * 获取某个具体company
     */
@Test
public void should_return_company_when_input_company_id(){
    Company company_1 = new Company("company_1");
    Company saveCompany = testEntityManager.persistFlushFind(company_1);
    Company company = companyRepository.findById(saveCompany.getId()).get();
    Assertions.assertThat(company.getId()).isEqualTo(saveCompany.getId());
    Assertions.assertThat(company.getName()).isEqualTo(saveCompany.getName());

}


/**
 * 获取某个具体company下所有employee列表
 */
    @Test
    public void should_return_employee_list_when_find_a_company_employeeList(){
        Company company_1 = new Company("company_1");
        List<Employee> employeeList = new ArrayList<>();
        Employee employee_1 = new Employee("employee_1");
        employeeList.add(employee_1);
        company_1.setEmployees(employeeList);
        Company save_company = testEntityManager.persistAndFlush(company_1);
        List<Employee> find_employeeList = companyRepository.findById(save_company.getId()).get().getEmployees();
        Assertions.assertThat(find_employeeList.size()).isEqualTo(1);
        Assertions.assertThat(find_employeeList.get(0).getName()).isEqualTo(save_company.getEmployees().get(0).getName());

    }



    /**
     * 分页查询，page等于2，size等于1
     */
    @Test
    public void should_return_companyList_when_input_page_and_pageSize() {
        Company company_1 = new Company("company_1");
        Company company_2 = new Company("company_2");
        testEntityManager.persistAndFlush(company_1);
        Company save_Company_2 = testEntityManager.persistAndFlush(company_2);
        Page<Company> companyList = companyRepository.findAll(new PageRequest(1, 1));
        Assertions.assertThat(companyList.getContent().size()).isEqualTo(1);
        Assertions.assertThat(companyList.getContent().get(0).getId()).isEqualTo(save_Company_2.getId());
        Assertions.assertThat(companyList.getContent().get(0).getName()).isEqualTo(save_Company_2.getName());
    }


    /**
     * 增加一个company
     */
    @Test
    public void should_return_company_when_add_company(){
        Company saveCompany = testEntityManager.persistFlushFind(new Company("company_1"));
        Company company = companyRepository.save(saveCompany);
        Assertions.assertThat(company.getName()).isEqualTo(saveCompany.getName());

    }


    /**
     * 更新某个company
     */
    @Test
    public void should_return_update_company_when_input_companyId_and_update_company_info() {
        Company company_1 = new Company("company-1");
        Company save_company_1 = testEntityManager.persistAndFlush(company_1);
        companyRepository.updateCompanyNameById("company1",save_company_1.getId());
        Company refreshCompany = testEntityManager.refresh(save_company_1);
        Assertions.assertThat(refreshCompany.getName()).isEqualTo("company1");
    }


    /**
     * 删除某个company以及名下所有employees
     */
    @Test
    public void should_return_delete_company_info_when_delete_a_company() {
        Company company_1 = new Company("company_1");
        List<Employee> employeeList = new ArrayList<>();
        Employee employee_1 = new Employee("employee_1");
        employeeList.add(employee_1);
        company_1.setEmployees(employeeList);
        Company save_company = testEntityManager.persistAndFlush(company_1);
        Employee find_employee = employeeRepository.findById(save_company.getEmployees().get(0).getId()).get();
        Assertions.assertThat(find_employee.getId()).isEqualTo(save_company.getEmployees().get(0).getId());
        companyRepository.delete(save_company);
        testEntityManager.flush();
        Company findcompany = testEntityManager.find(Company.class, save_company.getId());
        Employee findemployee = testEntityManager.find(Employee.class, save_company.getEmployees().get(0).getId());
        Assertions.assertThat(findcompany).isEqualTo(null);
        Assertions.assertThat(findemployee).isEqualTo(null);

    }

}