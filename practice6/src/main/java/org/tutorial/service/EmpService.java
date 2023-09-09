package org.tutorial.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.tutorial.model.DTO.EmpDTO;
import org.tutorial.model.PO.EmpPO;

public interface EmpService {

    EmpDTO addEmp(EmpPO empPO);

    EmpDTO updateEmp(EmpPO empPO);

    void deleteEmp(Integer empno);

    EmpDTO getOneEmp(Integer empno);

    List<EmpDTO> getAll();
    
    Page<EmpDTO> getEmpsByPageAndSort(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection);
    
    List<EmpDTO> getEmpsByDeptno(Integer deptno);

}
