package org.tutorial.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.tutorial.dao.EmpDAO;
import org.tutorial.dao.impl.EmpDAOImpl;
import org.tutorial.model.EmpDO;
import org.tutorial.service.EmpService;

public class EmpServiceImpl implements EmpService {

    private EmpDAO dao;

    public EmpServiceImpl() {
        dao = new EmpDAOImpl();
    }

    @Override
    public EmpDO addEmp(String ename, String job, LocalDate hiredate,
                        Double sal, Double comm, Integer deptno) {

        EmpDO empDO = new EmpDO();

        empDO.setEname(ename);
        empDO.setJob(job);
        empDO.setHiredate(hiredate);
        empDO.setSal(sal);
        empDO.setComm(comm);
        empDO.setDeptno(deptno);
        dao.insert(empDO);

        return empDO;
    }

    @Override
    public EmpDO updateEmp(Integer empno, String ename, String job,
                           LocalDate hiredate, Double sal, Double comm, Integer deptno) {

        EmpDO empDO = new EmpDO();

        empDO.setEmpno(empno);
        empDO.setEname(ename);
        empDO.setJob(job);
        empDO.setHiredate(hiredate);
        empDO.setSal(sal);
        empDO.setComm(comm);
        empDO.setDeptno(deptno);
        dao.update(empDO);

        return dao.findByPrimaryKey(empno);
    }

    @Override
    public void deleteEmp(Integer empno) {
        dao.delete(empno);
    }

    @Override
    public EmpDO getOneEmp(Integer empno) {
        return dao.findByPrimaryKey(empno);
    }

    @Override
    public List<EmpDO> getAll() {
        return dao.getAll();
    }

}
