package org.tutorial.dao;

import java.util.List;

import org.tutorial.model.DO.DeptDO;
import org.tutorial.model.DO.EmpDO;

public interface DeptDAO {

    void insert(DeptDO deptDO);

    void update(DeptDO deptDO);

    void delete(Integer deptno);

    DeptDO findByPrimaryKey(Integer deptno);

    List<DeptDO> getAll();

    List<EmpDO> getEmpsByDeptno(Integer deptno);

    List<DeptDO> findByCriteria(DeptDO deptDO);
}
