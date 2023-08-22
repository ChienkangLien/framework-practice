package org.tutorial.service.impl;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.tutorial.model.PO.DeptPO;
@SpringBootTest
@Transactional
public class DeptServiceImplTest {
	@Autowired
	private DeptServiceImpl service;

	@Test
	public void testInsert() {
		DeptPO deptDO = new DeptPO();
		deptDO.setDname("人資部");
		deptDO.setLoc("日本東金");
		service.insert(deptDO);
		System.out.println("dept insert complete!");
	}

	@Test
	public void testUpdate() {
		DeptPO deptDO = new DeptPO();
		deptDO.setDeptno(10);
		deptDO.setDname("財務部6667");
		deptDO.setLoc("臺灣台北6667");
		service.update(deptDO);
		System.out.println("dept update complete!");
	}

	@Test
	public void testDeleteDept() {
		service.deleteDept(48);
		System.out.println("dept delete complete!");
	}

	@Test
	public void testGetOneDept() {
		DeptPO deptDO = service.getOneDept(20);
		assertNotNull(deptDO);
		assertEquals((Integer)20, deptDO.getDeptno());
		assertEquals("研發部ww", deptDO.getDname());
		assertEquals("臺灣新竹吃1", deptDO.getLoc());
		System.out.println(deptDO);
	}

	@Test
	public void testGetAll() {
		List<DeptPO> list = service.getAll();
		assertNotNull(list);
		assertEquals(6, list.size());
		System.out.println(list);
	}

	
}