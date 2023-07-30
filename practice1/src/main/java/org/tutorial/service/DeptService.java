package org.tutorial.service;

import java.util.List;

import org.tutorial.model.DeptDO;
import org.tutorial.model.EmpDO;

public interface DeptService {

    List<DeptDO> getAll();

    DeptDO getOneDept(Integer deptno);

    DeptDO update(Integer deptno, String dname, String loc);

    List<EmpDO> getEmpsByDeptno(Integer deptno);

    void deleteDept(Integer deptno);

}
