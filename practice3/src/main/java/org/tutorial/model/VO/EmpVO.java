package org.tutorial.model.VO;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class EmpVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer empno;
	
	@NotBlank(message = "員工姓名: 請勿空白")
	@Pattern(regexp = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{2,10}$", message = "員工姓名:只能是中、英文字母、數字和_，且長度必需在2到10之間")
	private String ename;
	
	@NotBlank(message = "職位: 請勿空白")
	@Size(max = 9, message = "職位長度不能超過9個字符")
	private String job;
	
	@NotNull(message = "日期: 請勿空白")
	private LocalDate hiredate;
	
	@NotNull(message = "薪水: 請勿空白")
	private Double sal;
	
	@NotNull(message = "獎金: 請勿空白")
	private Double comm;
	
	private DeptVO deptVO;
}
