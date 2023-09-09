package org.tutorial.model.PO;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = { "deptPO" }) // 避免循環引用stackOverflow
@Table(name = "EMP2")
@ApiModel(description = "員工資料")
public class EmpPO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EMPNO", nullable = false, columnDefinition = "INT AUTO_INCREMENT")
	@ApiModelProperty(value = "員工編號", example ="1")
	private Integer empno;

	@Column(name = "ENAME", length = 10) // length是可選的描述
	@ApiModelProperty(value = "員工名稱", example ="王曉明")
	private String ename;

	@Column(name = "JOB", length = 9)
	@ApiModelProperty(value = "職位", example ="業務")
	private String job;

	@Column(name = "HIREDATE", columnDefinition = "DATE")
	@ApiModelProperty(value = "到職日", example ="2023-06-28")
	private LocalDate hiredate;

	@Column(name = "SAL", columnDefinition = "INT")
	@ApiModelProperty(value = "薪水", example ="2000.0")
	private Double sal;

	@Column(name = "COMM", columnDefinition = "INT")
	@ApiModelProperty(value = "獎金", example ="100.0")
	private Double comm;

	// 解決循環依賴，相較@JsonIgnore是完全跳過序列化，選擇其一序列化
	// Managed會序列化、Back不會
	// 最佳解是另建DTO
	@ManyToOne
	@JoinColumn(name = "DEPTNO")
//	@JsonManagedReference
	private DeptPO deptPO;
}
