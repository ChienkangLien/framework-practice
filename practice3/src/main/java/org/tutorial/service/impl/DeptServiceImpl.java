package org.tutorial.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tutorial.dao.DeptDAO;
import org.tutorial.model.DO.DeptDO;
import org.tutorial.model.DO.EmpDO;
import org.tutorial.service.DeptService;
@Service
public class DeptServiceImpl implements DeptService {
	@Autowired
    private DeptDAO dao;

	@Override
    public List<DeptDO> getAll() {
        return dao.getAll();
    }

	@Override
    public DeptDO getOneDept(Integer deptno) {
        return dao.findByPrimaryKey(deptno);
    }

	@Override
    public DeptDO update(DeptDO deptDO) {
        dao.update(deptDO);
        return dao.findByPrimaryKey(deptDO.getDeptno());
    }

	@Override
    public List<EmpDO> getEmpsByDeptno(Integer deptno) {
        return dao.getEmpsByDeptno(deptno);
    }

	@Override
    public void deleteDept(Integer deptno) {
        dao.delete(deptno);
    }

}
