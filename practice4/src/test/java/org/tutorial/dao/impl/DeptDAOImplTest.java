package org.tutorial.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.tutorial.config.AppConfig;
import org.tutorial.dao.DeptDAO;
import org.tutorial.model.DO.DeptDO;
import org.tutorial.model.DO.EmpDO;
@WebAppConfiguration // 如果配置類有@EnableWebMvc就要加
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class DeptDAOImplTest {
	@Autowired
	private DeptDAO dao;

	// JPA中persist、merge、remove這類持久化操作，為了確保事務的一致性和可靠性，JPA要求在進行持久化操作時，
	// 必須在一個事務上下文中進行，這樣可以保證在整個持久化操作過程中發生任何異常時都能正確地回滾事務
	@Transactional
	@Test
	public void testInsert() {
		DeptDO deptDO = new DeptDO();
		deptDO.setDname("製造部");
		deptDO.setLoc("德國漢堡");
		dao.insert(deptDO);
		System.out.println("dept insert complete!");
	}

	@Test
	public void testUpdate() {
		DeptDO deptDO = new DeptDO();
		deptDO.setDeptno(10);
		deptDO.setDname("財務部6");
		deptDO.setLoc("臺灣台北6");
		dao.update(deptDO);
		System.out.println("dept update complete!");
	}

	@Transactional
	@Test
	public void testDelete() {
		dao.delete(54);
		System.out.println("dept delete complete!");
	}

	@Test
	public void testFindByPrimaryKey() {
		DeptDO deptDO = dao.findByPrimaryKey(20);
		assertNotNull(deptDO);
		assertEquals((Integer)20, deptDO.getDeptno());
		assertEquals("研發部", deptDO.getDname());
		assertEquals("臺灣新竹", deptDO.getLoc());
		System.out.println(deptDO);
	}

	@Test
	public void testGetAll() {
		List<DeptDO> list = dao.getAll();
		assertNotNull(list);
		assertEquals(6, list.size());
		System.out.println(list);
	}

	@Test
	public void testGetEmpsByDeptno() {
		List<EmpDO> list = dao.getEmpsByDeptno(20);
		assertNotNull(list);
		assertEquals(5, list.size());
		System.out.println(list);
	}
}