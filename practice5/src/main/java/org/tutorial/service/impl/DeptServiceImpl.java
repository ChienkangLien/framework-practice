package org.tutorial.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tutorial.model.PO.DeptPO;
import org.tutorial.repository.DeptRepository;
import org.tutorial.service.DeptService;
@Service
@Transactional
public class DeptServiceImpl implements DeptService {
	@Autowired
    private DeptRepository repository;

	@Override
    public List<DeptPO> getAll() {
        return repository.findAll();
    }

    @Override
    public DeptPO update(DeptPO deptPO) {
    	if (repository.findById(deptPO.getDeptno()).isPresent()) { // Optional不能用==null來判斷
    		repository.save(deptPO);
    	}
    	return repository.findById(deptPO.getDeptno()).orElse(null); // Optional需要處理not Present的狀況
    }

    @Override
    public DeptPO getOneDept(Integer deptno) {
    	return repository.findById(deptno).orElse(null);
    }

    @Override
    public void deleteDept(Integer deptno) {
    	if (repository.findById(deptno).isPresent()) { // Optional不能用==null來判斷
    		repository.deleteById(deptno);
    	}
    }

	@Override
	public DeptPO insert(DeptPO deptPO) {
		return repository.save(deptPO);
	}

}
