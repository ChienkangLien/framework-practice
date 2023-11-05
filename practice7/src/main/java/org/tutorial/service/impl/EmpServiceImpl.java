package org.tutorial.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
	@CachePut(value = "empCache", key = "#empPO.empno")
	public EmpPO updateEmp(EmpPO empPO) {
		if (repository.findById(empPO.getEmpno()).isPresent()) { // Optional不能用==null來判斷
			repository.save(empPO);
		}
		return repository.findById(empPO.getEmpno()).orElse(null); // Optional需要處理not Present的狀況
	}

	@Override
	@CacheEvict(value = "empCache", key = "#empno")
	public void deleteEmp(Integer empno) {
		if (repository.findById(empno).isPresent()) { // Optional不能用==null來判斷
    		repository.deleteById(empno);
    	}
	}

	@Override
	@Cacheable(value = "empCache", key = "#empno")
	public EmpPO getOneEmp(Integer empno) {
		return repository.findById(empno).orElse(null);
	}

	@Override
	@Cacheable(value = "empCache")
	public List<EmpPO> getAll() {
		return repository.findAll();
	}
	
	@Override
	@Cacheable(value = "empCache", key = "'page' + #pageNumber + #pageSize + #sortField + #sortDirection")
	public Page<EmpPO> getEmpsByPageAndSort(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortField));
        return repository.findAll(pageable);
    }

	@Override
	@Cacheable(value = "empByDeptnoCache", key = "#deptno")
	public List<EmpPO> getEmpsByDeptno(Integer deptno) {
		return repository.findByDeptPODeptno(deptno);
	}

}
