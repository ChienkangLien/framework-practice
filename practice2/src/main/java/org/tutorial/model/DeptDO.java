package org.tutorial.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "DEPT2")
@NamedQueries({
	@NamedQuery(name = "DeptDO.findAll", query = "SELECT d FROM DeptDO d"),
	@NamedQuery(name = "DeptDO.findByDeptno", query = "SELECT d FROM DeptDO d LEFT JOIN FETCH d.empDOs WHERE d.deptno = :deptno")
})
public class DeptDO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DEPTNO", nullable = false, columnDefinition = "INT AUTO_INCREMENT")
	private Integer deptno;
	
	@Column(name = "DNAME", columnDefinition = "VARCHAR(14)")
	private String dname;
	
	@Column(name = "LOC", columnDefinition = "VARCHAR(13)")
	private String loc;
	
	@OneToMany(mappedBy = "deptDO", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private List<EmpDO> empDOs;
}
