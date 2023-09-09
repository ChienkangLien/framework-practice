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

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Entity
@Table(name = "DEPT2")
@ApiModel(description = "部門資料")
public class DeptPO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "部門編號", example ="1")
	@Column(name = "DEPTNO", nullable = false, columnDefinition = "INT AUTO_INCREMENT")
	private Integer deptno;

	@Column(name = "DNAME", columnDefinition = "VARCHAR(14)")
	@ApiModelProperty(value = "部門名稱", example ="資訊部")
	private String dname;

	@Column(name = "LOC", columnDefinition = "VARCHAR(13)")
	@ApiModelProperty(value = "部門地點", example ="台灣台北")
	private String loc;

	// 解決循環依賴，相較@JsonIgnore是完全跳過序列化，選擇其一序列化
	// Managed會序列化、Back不會
	// 最佳解是另建DTO
	@OneToMany(mappedBy = "deptPO", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
//	@JsonBackReference
	private List<EmpPO> empPOs;
}
