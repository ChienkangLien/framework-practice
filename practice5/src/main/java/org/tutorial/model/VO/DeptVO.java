package org.tutorial.model.VO;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class DeptVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer deptno;

	@NotBlank(message = "部門名稱: 請勿空白")
	@Pattern(regexp = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$", message = "部門名稱: 只能是中、英文字母、數字和_，且長度必需在2到10之間")
	private String dname;

	@NotBlank(message = "部門基地: 請勿空白")
	private String loc;

	private List<EmpVO> empVOs;
}
