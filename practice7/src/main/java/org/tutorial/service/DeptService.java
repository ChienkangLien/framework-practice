package org.tutorial.service;

import java.util.List;

import org.tutorial.model.PO.DeptPO;

public interface DeptService {

    List<DeptPO> getAll();

    DeptPO getOneDept(Integer deptno);

    DeptPO update(DeptPO deptPO);

    void deleteDept(Integer deptno);

    DeptPO insert(DeptPO deptPO);
}
