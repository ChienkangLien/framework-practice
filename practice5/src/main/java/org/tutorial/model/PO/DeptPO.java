package org.tutorial.model.PO;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "DEPT2")
public class DeptPO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DEPTNO", nullable = false, columnDefinition = "INT AUTO_INCREMENT")
	private Integer deptno;

	@Column(name = "DNAME", columnDefinition = "VARCHAR(14)")
	private String dname;

	@Column(name = "LOC", columnDefinition = "VARCHAR(13)")
	private String loc;

	@OneToMany(mappedBy = "deptPO", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private List<EmpPO> empPOs;
}
