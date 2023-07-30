package org.tutorial.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tutorial.dao.EmpDAO;
import org.tutorial.model.DO.EmpDO;
import org.tutorial.service.EmpService;

@Service
@Transactional
public class EmpServiceImpl implements EmpService {
	@Autowired
	private EmpDAO dao;

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
