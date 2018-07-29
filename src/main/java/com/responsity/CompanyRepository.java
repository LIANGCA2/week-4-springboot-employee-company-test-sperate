package com.responsity;

import com.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findByName(String name);


    @Query(value = "update company set name = ?1 where id = ?2",nativeQuery = true)
    @Modifying
    void updateCompanyNameById(String name, Long id);
}
