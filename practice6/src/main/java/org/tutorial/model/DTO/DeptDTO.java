package org.tutorial.model.DTO;


import java.io.Serializable;
import java.util.List;


import lombok.Data;
@Data
public class DeptDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer deptno;

	private String dname;

	private String loc;

	private List<EmpDTO> empDTOs;
}
