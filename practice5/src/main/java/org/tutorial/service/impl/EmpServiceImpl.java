package org.tutorial.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.tutorial.model.PO.EmpPO;
import org.tutorial.repository.EmpRepository;
import org.tutorial.service.EmpService;

@Service
@Transactional
public class EmpServiceImpl implements EmpService {
	@Autowired
	private EmpRepository repository;

	@Override
	public EmpPO addEmp(EmpPO empPO) {
		return repository.save(empPO);
	}

	@Override
	public EmpPO updateEmp(EmpPO empPO) {
		if (repository.findById(empPO.getEmpno()).isPresent()) { // Optional不能用==null來判斷
			repository.save(empPO);
		}
		return repository.findById(empPO.getEmpno()).orElse(null); // Optional需要處理not Present的狀況
	}

	@Override
	public void deleteEmp(Integer empno) {
		if (repository.findById(empno).isPresent()) { // Optional不能用==null來判斷
    		repository.deleteById(empno);
    	}
	}

	@Override
	public EmpPO getOneEmp(Integer empno) {
		return repository.findById(empno).orElse(null);
	}

	@Override
	public List<EmpPO> getAll() {
		return repository.findAll();
	}
	
	@Override
	public Page<EmpPO> getEmpsByPageAndSort(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortField));
        return repository.findAll(pageable);
    }

	@Override
	public List<EmpPO> getEmpsByDeptno(Integer deptno) {
		return repository.findByDeptPODeptno(deptno);
	}

}
