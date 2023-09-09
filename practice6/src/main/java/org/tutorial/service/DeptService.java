package org.tutorial.service;

import java.util.List;

import org.tutorial.model.DTO.DeptDTO;
import org.tutorial.model.PO.DeptPO;

public interface DeptService {

    List<DeptDTO> getAll();

    DeptDTO getOneDept(Integer deptno);

    DeptDTO update(DeptPO deptPO);

    void deleteDept(Integer deptno);

    DeptDTO insert(DeptPO deptPO);
}
