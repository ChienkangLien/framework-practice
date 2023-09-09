package org.tutorial.model.DTO;

import java.io.Serializable;
import java.time.LocalDate;


import lombok.Data;

@Data
public class EmpDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer empno;

	private String ename;

	private String job;

	private LocalDate hiredate;

	private Double sal;

	private Double comm;

	private DeptDTO deptDTO;
}
