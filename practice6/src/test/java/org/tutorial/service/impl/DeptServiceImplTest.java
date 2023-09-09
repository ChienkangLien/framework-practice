package org.tutorial.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.tutorial.model.DTO.DeptDTO;
import org.tutorial.model.PO.DeptPO;
import org.tutorial.repository.DeptRepository;

@SpringBootTest
public class DeptServiceImplTest {
	@Autowired
	private DeptRepository repository;
	@Test
	public void testGetOneDept() {
		System.out.println(convertDeptToDTO(repository.findById(20).orElse(null)));
//		return convertDeptToDTO(repository.findById(deptno).orElse(null));
	}
	
	public DeptDTO convertDeptToDTO(DeptPO deptPO) {
		System.out.println(111);
		DeptDTO deptDTO = new DeptDTO();
		deptDTO.setDeptno(deptPO.getDeptno());
		deptDTO.setDname(deptPO.getDname());
		deptDTO.setLoc(deptPO.getLoc());

		return deptDTO;
	}
}
