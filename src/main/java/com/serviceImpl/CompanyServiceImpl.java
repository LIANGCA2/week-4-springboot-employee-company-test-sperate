package com.serviceImpl;

import com.dto.CompanyDTO;
import com.model.Company;
import com.model.Employee;
import com.responsity.CompanyRepository;
import com.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service("companyService")
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public List<Company> findAllCompany(Pageable pageable) {
        return companyRepository.findAll(pageable).getContent();
    }

    @Override
    public CompanyDTO findCompanyById(Long id) {
        return new CompanyDTO(companyRepository.findById(id).get());

    }

//    @Override
//    public List<Company> getCompanyByPageAndpageSize(Integer page, Integer pageSize) {
//        return companyRepository.findAll(new PageRequest(page-1, pageSize)).getContent();
//    }

    @Override
    public Company addCompany(Company company) {
        company.getEmployees().stream().forEach(employee -> {
            employee.setCompany(company);
        });
        return companyRepository.save(company);
    }

    @Override
    public void updateCompany(Long id, Company company) {
        company.getEmployees().stream().filter(employee -> employee.getCompany() == null).forEach(employee -> {
            employee.setCompany(company);
        });
        company.setId(id);
        companyRepository.save(company);
    }

    @Override
    public Company deleteCompany(Long id) {
        Company one = companyRepository.findById(id).get();
        companyRepository.delete(one);
        return one;
    }
    @Override
    public List<Employee> findEmployeeByCompanyId(Long companyId) {
        return companyRepository.findById(companyId).get().getEmployees();
    }
}
