package org.tutorial.service.impl;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;
import org.tutorial.model.PO.DeptPO;
import org.tutorial.model.PO.EmpPO;

@SpringBootTest
@Transactional
public class EmpServiceImplTest {
	@Autowired
	private EmpServiceImpl service;

	@Test
	public void testAddEmp() {
		EmpPO empPO1 = new EmpPO();
		empPO1.setEname("王小明789");
		empPO1.setJob("manager");
		empPO1.setHiredate(LocalDate.parse(("2023-04-01")));
		empPO1.setSal((double) 50000);
		empPO1.setComm((double) 500);
		DeptPO deptPO = new DeptPO();
		deptPO.setDeptno(10);
		empPO1.setDeptPO(deptPO);
		assertTrue(service.addEmp(empPO1).getEmpno() > 0);
	}

	@Test
	public void testUpdateEmp() {
		EmpPO empPO2 = new EmpPO();
		empPO2.setEmpno(16);
		empPO2.setEname("王2");
		empPO2.setJob("ma2");
		empPO2.setHiredate(LocalDate.parse(("2020-04-01")));
		empPO2.setSal((double) 20000);
		empPO2.setComm((double) 200);
		DeptPO deptPO = new DeptPO();
		deptPO.setDeptno(20);
		empPO2.setDeptPO(deptPO);
		System.out.println("UpdateEmp : " + service.updateEmp(empPO2));

	}

	@Test
	public void testDeleteEmp() {
		EmpPO empPO3 = new EmpPO();
		empPO3.setEmpno(20);
		service.deleteEmp(empPO3.getEmpno());
	}

	@Test
	public void testGetOneEmp() {
		EmpPO empPO4 = service.getOneEmp(1);
		assertEquals((Integer) 1, empPO4.getEmpno());
		assertEquals("king22", empPO4.getEname());
		assertEquals("president", empPO4.getJob());
		assertEquals((Integer) 20, empPO4.getDeptPO().getDeptno());
	}

	@Test
	public void testGetAll() {
		List<EmpPO> list = service.getAll();
		for (EmpPO empPO : list) {
			System.out.print(empPO.getEmpno() + ",");
			System.out.print(empPO.getEname() + ",");
			System.out.print(empPO.getJob() + ",");
			System.out.print(empPO.getHiredate() + ",");
			System.out.print(empPO.getSal() + ",");
			System.out.print(empPO.getComm() + ",");
			System.out.print(empPO.getDeptPO());
			System.out.println();
		}
	}
	
	@Test
	public void testGetAllByPage() {
		Page<EmpPO> list = service.getEmpsByPageAndSort(0, 3, "sal", Direction.DESC);
		
		for (EmpPO empPO : list) {
			System.out.print(empPO.getEmpno() + ",");
			System.out.print(empPO.getEname() + ",");
			System.out.print(empPO.getJob() + ",");
			System.out.print(empPO.getHiredate() + ",");
			System.out.print(empPO.getSal() + ",");
			System.out.print(empPO.getComm() + ",");
			System.out.print(empPO.getDeptPO());
			System.out.println();
		}
	}

	@Test
	public void testGetEmpsByDeptno() {
		List<EmpPO> list = service.getEmpsByDeptno(20);
		assertNotNull(list);
		assertEquals(8, list.size());
		System.out.println(list);
	}
}
