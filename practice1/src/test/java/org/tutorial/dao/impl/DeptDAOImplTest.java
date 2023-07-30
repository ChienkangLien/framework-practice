package org.tutorial.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tutorial.dao.DeptDAO;
import org.tutorial.model.DeptDO;
import org.tutorial.model.EmpDO;

public class DeptDAOImplTest {

	private DeptDAO dao;

	@Before
	public void setUp() {
		dao = new DeptDAOImpl();
	}

	@After
	public void tearDown() {
		dao = null;
	}

	@Test
	public void testInsert() {
		DeptDO deptDO = new DeptDO();
		deptDO.setDname("製造部");
		deptDO.setLoc("美國洛杉磯");
		dao.insert(deptDO);
		System.out.println("dept insert complete!");
	}

	@Test
	public void testUpdate() {
		DeptDO deptDO = new DeptDO();
		deptDO.setDeptno(10);
		deptDO.setDname("財務部2");
		deptDO.setLoc("臺灣台北2");
		dao.update(deptDO);
		System.out.println("dept update complete!");
	}

	@Test
	public void testDelete() {
		dao.delete(30);
		System.out.println("dept delete complete!");
	}

	@Test
	public void testFindByPrimaryKey() {
		DeptDO deptDO = dao.findByPrimaryKey(20);
		assertNotNull(deptDO);
		assertEquals((Integer)20, deptDO.getDeptno());
		assertEquals("研發部", deptDO.getDname());
		assertEquals("臺灣新竹", deptDO.getLoc());
	}

	@Test
	public void testGetAll() {
		List<DeptDO> list = dao.getAll();
		assertNotNull(list);
		assertEquals(5, list.size());
	}

	@Test
	public void testGetEmpsByDeptno() {
		List<EmpDO> list = dao.getEmpsByDeptno(10);
		assertNotNull(list);
		assertEquals(3, list.size());
	}
}