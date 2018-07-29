package com.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Employee;
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
@WebMvcTest(EmployeeController.class)
@EnableSpringDataWebSupport
public class EmployeeControllerTest {

    @Autowired
    private EmployeeController employeeController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper mapper;


    @Test
    public void should_return_all_Employee() throws Exception {
        //given
        Employee employee_1 = new Employee("employee-1", "male");
        Employee employee_2 = new Employee("employee-2", "female");
        List<Employee> employeeList = Arrays.asList(employee_1, employee_2);
        //when
        when(employeeService.findAllEmployee(any())).thenReturn(employeeList);
        //then
        ResultActions resultActions = mockMvc.perform(get("/employees").contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("employee-1")))
                .andExpect(jsonPath("$[0].gender", containsString("male")))
                .andExpect(jsonPath("$[1].name", is("employee-2")))
                .andExpect(jsonPath("$[1].gender", is("female")));

    }

    @Test
    public void should_return_one_employee_when_input_employee_id() throws Exception {
        //given
        Employee employee_1 = new Employee("employee-1", "male");
        //when
        when(employeeService.findOneOfEmployee(any())).thenReturn(employee_1);
        //then
        ResultActions resultActions = mockMvc.perform(get("/employees/id={param}", 1));
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("employee-1")))
                .andExpect(jsonPath("$.gender", is("male")));


    }


    @Test
    public void should_return_employeeList_when_input_employee_gender() throws Exception {
        //given
        String gender = "male";
        Employee employee_1 = new Employee("employee-1", "male");
        Employee employee_2 = new Employee("employee-2", "male");
        List<Employee> employeeList = Arrays.asList(employee_1, employee_2);
        //when
        when(employeeService.findEmployeeByGender(gender)).thenReturn(employeeList);
        //then
        ResultActions resultActions = mockMvc.perform(get("/employees/gender={param}", gender));
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("employee-1")))
                .andExpect(jsonPath("$[0].gender", is("male")))
                .andExpect(jsonPath("$[1].name", is("employee-2")))
                .andExpect(jsonPath("$[1].gender", is("male")));
    }

    @Test
    public void should_return_employee_when_add_a_emplyee() throws Exception {
        //given
        Employee employee_1 = new Employee("employee-1", "male");
        employee_1.setId((long) 1);
        //when
        when(employeeService.addEmployee(any())).thenReturn(employee_1);
        //then

        ResultActions resultActions = mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new Employee("employee-1", "male"))));
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("employee-1")))
                .andExpect(jsonPath("$.gender", is("male")))
                .andExpect(jsonPath("$.id", is(1)));


    }


    @Test
    public void should_return_delete_employee_when_delete_a_emplyee() throws Exception {
        //given
        Long id = Long.valueOf(1);
        Employee employee_1 = new Employee("employee-1", "male");
        employee_1.setId((long) 1);
        Employee employee_2 = new Employee("employee-2","male");
        List<Employee> employeeList = Arrays.asList( employee_2);
        //when
        when(employeeService.deleteEmployee(id)).thenReturn(employeeList);
        //then

        ResultActions resultActions = mockMvc.perform(delete("/employees/{id}",id)
                .contentType(MediaType.APPLICATION_JSON)
                );
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("employee-2")))
                .andExpect(jsonPath("$[0].gender", is("male")));


    }
    @Test
    public void should_return_update_employee_info_when_update_a_employee_by_id() throws Exception {
        //given
        Long id = Long.valueOf(1);
        Employee employee_1 = new Employee("employee-1", "male");
        Employee employee_2 = new Employee("employee-1", "female");
        List<Employee> employeeList = Arrays.asList(employee_2);
        //when
        when(employeeService.updateEmployee(id,employee_1)).thenReturn(employeeList);
        //then
        ResultActions resultActions = mockMvc.perform(put("/employees/{id}",id).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(employee_2)));
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].name",is("employee-1")))
                .andExpect(jsonPath("$[0].gender",is("female")));


    }





}