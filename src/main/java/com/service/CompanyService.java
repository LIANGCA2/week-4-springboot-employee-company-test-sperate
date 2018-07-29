package com.service;



import com.dto.CompanyDTO;
import com.model.Company;
import com.model.Employee;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyService {


    List<Company> findAllCompany(Pageable pageable);

    CompanyDTO findCompanyById(Long id);

   // List<Company> getCompanyByPageAndpageSize(Integer page, Integer pageSize);

    Company addCompany(Company company);

    void updateCompany(Long id, Company company);

    Company deleteCompany(Long id);
    List<Employee> findEmployeeByCompanyId(Long companyId);
}
