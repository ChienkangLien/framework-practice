package org.tutorial.service.impl;

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
	public EmpDO addEmp(EmpDO empDO) {

		dao.insert(empDO);
		return empDO;
	}

	@Override
	public EmpDO updateEmp(EmpDO empDO) {
		dao.update(empDO);
		return dao.findByPrimaryKey(empDO.getEmpno());
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
