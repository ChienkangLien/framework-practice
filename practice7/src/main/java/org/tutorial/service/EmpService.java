package org.tutorial.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.tutorial.model.PO.EmpPO;

public interface EmpService {

    EmpPO addEmp(EmpPO empPO);

    EmpPO updateEmp(EmpPO empPO);

    void deleteEmp(Integer empno);

    EmpPO getOneEmp(Integer empno);

    List<EmpPO> getAll();
    
    Page<EmpPO> getEmpsByPageAndSort(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection);
    
    List<EmpPO> getEmpsByDeptno(Integer deptno);

}
