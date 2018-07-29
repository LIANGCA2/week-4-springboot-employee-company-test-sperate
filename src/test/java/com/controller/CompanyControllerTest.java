package com.controller;

import com.dto.CompanyDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Company;
import com.model.Employee;
import com.service.CompanyService;
import com.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(CompanyController.class)
@EnableSpringDataWebSupport
public class CompanyControllerTest {

    @Autowired
    private CompanyController companyController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @Autowired
    private ObjectMapper mapper;


    @Test
    public void should_return_all_Employee() throws Exception {
        //given
        Company company_1 = new Company("company_1");
        Company company_2 = new Company("company_2");
        List<Company> companyList = Arrays.asList(company_1, company_2);
        //when
        when(companyService.findAllCompany(any())).thenReturn(companyList);
        //then
        ResultActions resultActions = mockMvc.perform(get("/companies").contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("company_1")))
                .andExpect(jsonPath("$[1].name", is("company_2")));
    }

    @Test
    public void should_return_one_Company_when_input_company_id() throws Exception {
        //given
        CompanyDTO companydto = new CompanyDTO(new Company("company_1"));

        //when
        when(companyService.findCompanyById(Long.valueOf(1))).thenReturn(companydto);
        //then
        ResultActions resultActions = mockMvc.perform(get("/companies/{param}", 1));
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("company_1")));

    }



    @Test
    public void should_return_company_when_add_a_company() throws Exception {
        //given
        Company company_1 = new Company("company_1");
        company_1.setId((long) 1);
        //when
        when(companyService.addCompany(any())).thenReturn(company_1);
        //then

        ResultActions resultActions = mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new Company("company_1"))));
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("company_1")))
                .andExpect(jsonPath("$.id", is(1)));


    }


    @Test
    public void should_return_delete_company_when_delete_a_company() throws Exception {
        //given
        Long id = Long.valueOf(1);
        Company company_1 = new Company("company_1");
        company_1.setId((long) 1);

        //when
        when(companyService.deleteCompany(id)).thenReturn(company_1);
        //then

        ResultActions resultActions = mockMvc.perform(delete("/companies/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("company_1")));


    }

    @Test
    public void should_return_update_employee_info_when_update_a_employee_by_id() throws Exception {
        //given
        Employee employee_1 = new Employee("employee-1", "male");
        //then
        ResultActions resultActions = mockMvc.perform(put("/companies/{id}", Long.valueOf(1)).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(employee_1)));
        resultActions.andExpect(status().is2xxSuccessful());


    }


    @Test
    public void should_return_employeeList_when_input_companyId() throws Exception {
        //given
        Long id = Long.valueOf(1);
        Employee employee_1 = new Employee("employee-1", "male");
        List<Employee> employeeList = Arrays.asList(employee_1);
        when(companyService.findEmployeeByCompanyId(id)).thenReturn(employeeList);
        //then
        ResultActions resultActions = mockMvc.perform(get("/companies/{id}/employees", id));
        resultActions.andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name",is("employee-1")))
        .andExpect(jsonPath("$[0].gender",is("male")));

    }


}