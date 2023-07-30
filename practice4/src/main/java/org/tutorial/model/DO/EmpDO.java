package org.tutorial.model.DO;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = {"deptDO"}) // 避免循環引用stackOverflow
@Table(name = "EMP2")
@NamedQuery(name = "EmpDO.findAll", query = "SELECT e FROM EmpDO e")
public class EmpDO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EMPNO", nullable = false, columnDefinition = "INT AUTO_INCREMENT")
	private Integer empno;
	
	@Column(name = "ENAME", columnDefinition = "VARCHAR(10)")
	private String ename;
	
	@Column(name = "JOB", columnDefinition = "VARCHAR(9)")
	private String job;
	
	@Column(name = "HIREDATE", columnDefinition = "DATE")
	private LocalDate hiredate;
	@Column(name = "SAL", columnDefinition = "INT")
	private Double sal;
	
	@Column(name = "COMM", columnDefinition = "INT")
	private Double comm;
	
	@ManyToOne
	@JoinColumn(name = "DEPTNO")
	private DeptDO deptDO;
}
