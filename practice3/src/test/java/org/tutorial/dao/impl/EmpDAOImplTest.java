package org.tutorial.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.tutorial.AppConfig;
import org.tutorial.dao.EmpDAO;
import org.tutorial.model.DO.DeptDO;
import org.tutorial.model.DO.EmpDO;
@WebAppConfiguration // 如果配置類有@EnableWebMvc就要加
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class EmpDAOImplTest {
	@Autowired
	private EmpDAO dao;

//	@Before
//	public void setUp() {
//		dao = new EmpDAOImpl();
//	}
//
//	@After
//	public void tearDown() {
//		dao = null;
//	}

	@Test
	public void testInsert() {
		EmpDO empDO1 = new EmpDO();
		empDO1.setEname("王小明4");
		empDO1.setJob("manager");
		empDO1.setHiredate(LocalDate.parse(("2023-04-01")));
		empDO1.setSal((double) 50000);
		empDO1.setComm((double) 500);
		DeptDO deptDO = new DeptDO();
		deptDO.setDeptno(10);
		empDO1.setDeptDO(deptDO);
		int key = dao.insert(empDO1);
		assertTrue(key > 0);
	}

	@Test
	public void testUpdate() {
		EmpDO empDO2 = new EmpDO();
		empDO2.setEmpno(19);
		empDO2.setEname("王小明2");
		empDO2.setJob("manager2");
		empDO2.setHiredate(LocalDate.parse(("2020-04-01")));
		empDO2.setSal((double) 20000);
		empDO2.setComm((double) 200);
		DeptDO deptDO = new DeptDO();
		deptDO.setDeptno(20);
		empDO2.setDeptDO(deptDO);
		dao.update(empDO2);
	}

	@Test
	public void testdelete() {
		EmpDO empDO3 = new EmpDO();
		empDO3.setEmpno(21);
		dao.delete(empDO3.getEmpno());
	}

	@Test
	public void findByPrimaryKey() {
		EmpDO empDO4 = dao.findByPrimaryKey(1);
		assertEquals((Integer) 1, empDO4.getEmpno());
		assertEquals("king", empDO4.getEname());
		assertEquals("president", empDO4.getJob());
		assertEquals((Integer) 10, empDO4.getDeptDO().getDeptno());
	}

	@Test
	public void getAll() {
		List<EmpDO> list = dao.getAll();
		for (EmpDO empDO : list) {
			System.out.print(empDO.getEmpno() + ",");
			System.out.print(empDO.getEname() + ",");
			System.out.print(empDO.getJob() + ",");
			System.out.print(empDO.getHiredate() + ",");
			System.out.print(empDO.getSal() + ",");
			System.out.print(empDO.getComm() + ",");
			System.out.print(empDO.getDeptDO());
			System.out.println();
		}
	}
}
