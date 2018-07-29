package com.util;


import com.model.Company;
import com.model.Employee;

import java.util.List;

public class EqualUtil {



    public static boolean EmployeeListisEqual(List<Employee> list1, List<Employee> list2){

        if(list1.size()!=list2.size()){
            return false;
        }
        else{
            for(int i=0;i<list1.size();i++){

                if(!list1.get(i).equals(list2.get(i))){
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean CompanyListisEqual(List<Company> list1, List<Company> list2){

        if(list1.size()!=list2.size()){
            return false;
        }
        else{
            for(int i=0;i<list1.size();i++){

                if(!list1.get(i).equals(list2.get(i))){
                    System.out.println();
                    return false;
                }
            }
        }
        return true;
    }
}
