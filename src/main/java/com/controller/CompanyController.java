package com.controller;

import com.dto.CompanyDTO;
import com.model.Company;
import com.model.Employee;
import com.responsity.CompanyRepository;
import com.responsity.EmployeeRepository;
import com.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

   @Autowired
    private CompanyService companyService;


    @Transactional
    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Company save(@RequestBody Company company) {

        return  companyService.addCompany(company);
    }

    @Transactional
    @GetMapping(path="", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Company> findAll(Pageable pageable){
        return companyService.findAllCompany(pageable);
    }


    @Transactional
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@PathVariable Long id,@RequestBody Company company) {
        companyService.updateCompany(id,company);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Transactional
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompanyDTO get(@PathVariable("id")Long id) {
        return companyService.findCompanyById(id);
    }

    @Transactional
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Company delete(@PathVariable("id")Long id) {
        return companyService.deleteCompany(id);
    }


//    @GetMapping("/page/{page}/pageSize/{pageSize}")
//    @ResponseBody
//    public List<Company> selectCompanyByPageAndPageSize(@PathVariable Integer page, @PathVariable Integer pageSize) {
//        return companyService.getCompanyByPageAndpageSize(page,pageSize);
//    }


    @GetMapping("/{id}/employees")
    @ResponseBody
    public List<Employee> findEmployeeByCompanyId(@PathVariable Long id) {
        return companyService.findEmployeeByCompanyId(id);
    }

}
