package org.tutorial.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.tutorial.model.PO.DeptPO;
import org.tutorial.model.PO.EmpPO;
import org.tutorial.model.VO.DeptVO;
import org.tutorial.model.VO.EmpVO;

public class ConvertToVOUtil {

	// 透過ModelMapper自動映射屬性、取代手動方式
	public static List<DeptVO> convertToDeptVOList(List<DeptPO> deptPOs) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.createTypeMap(EmpPO.class, EmpVO.class);

		return deptPOs.stream().map(deptPO -> {
			DeptVO deptVO = modelMapper.map(deptPO, DeptVO.class);
			List<EmpPO> empPOs = deptPO.getEmpPOs();
			List<EmpVO> empVOs = empPOs.stream().map(empPO -> modelMapper.map(empPO, EmpVO.class))
					.collect(Collectors.toList());
			deptVO.setEmpVOs(empVOs);
			return deptVO;
		}).collect(Collectors.toList());

	}

	public static List<EmpVO> convertToEmpVOList(List<EmpPO> empPOs) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.createTypeMap(DeptPO.class, DeptVO.class);

		return empPOs.stream().map(empPO -> {
			EmpVO empVO = modelMapper.map(empPO, EmpVO.class);
			DeptVO deptVO = modelMapper.map(empPO.getDeptPO(), DeptVO.class);
			empVO.setDeptVO(deptVO);
			return empVO;
		}).collect(Collectors.toList());

	}

	public static DeptVO convertToDeptVO(DeptPO deptPO) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.createTypeMap(EmpPO.class, EmpVO.class);

		DeptVO deptVO = modelMapper.map(deptPO, DeptVO.class);
		List<EmpVO> empVOs = deptPO.getEmpPOs().stream().map(empPO -> modelMapper.map(empPO, EmpVO.class))
				.collect(Collectors.toList());
		deptVO.setEmpVOs(empVOs);
		return deptVO;
	}

	public static EmpVO convertToEmpVO(EmpPO empPO) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.createTypeMap(DeptPO.class, DeptVO.class);

		EmpVO empVO = modelMapper.map(empPO, EmpVO.class);
		DeptVO deptVO = modelMapper.map(empPO.getDeptPO(), DeptVO.class);
		empVO.setDeptVO(deptVO);
		return empVO;
	}
}
