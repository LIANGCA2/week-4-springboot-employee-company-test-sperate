package com.responsity;


import com.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
    List<Employee> findByGender(String gender);



    @Transactional
    @Query(value = "update employee set id = ?2 where id = ?1",nativeQuery = true)
    @Modifying
    void updateEmployeeIdById(Long oldId,Long newId);
}
