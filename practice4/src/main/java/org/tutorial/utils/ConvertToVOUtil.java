package org.tutorial.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.tutorial.model.DO.DeptDO;
import org.tutorial.model.DO.EmpDO;
import org.tutorial.model.VO.DeptVO;
import org.tutorial.model.VO.EmpVO;

public class ConvertToVOUtil {

	// 透過ModelMapper自動映射屬性、取代手動方式
	public static List<DeptVO> convertToDeptVOList(List<DeptDO> deptDOs) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.createTypeMap(EmpDO.class, EmpVO.class);

		return deptDOs.stream().map(deptDO -> {
			DeptVO deptVO = modelMapper.map(deptDO, DeptVO.class);
			List<EmpDO> empDOs = deptDO.getEmpDOs();
			List<EmpVO> empVOs = empDOs.stream().map(empDO -> modelMapper.map(empDO, EmpVO.class))
					.collect(Collectors.toList());
			deptVO.setEmpVOs(empVOs);
			return deptVO;
		}).collect(Collectors.toList());

//		    return deptDOs.stream().map(deptDO -> {
//				DeptVO deptVO = new DeptVO();
//				deptVO.setDeptno(deptDO.getDeptno());
//				deptVO.setDname(deptDO.getDname());
//				deptVO.setLoc(deptDO.getLoc());
//				return deptVO;
//			}).collect(Collectors.toList());
	}

	public static List<EmpVO> convertToEmpVOList(List<EmpDO> empDOs) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.createTypeMap(DeptDO.class, DeptVO.class);

		return empDOs.stream().map(empDO -> {
			EmpVO empVO = modelMapper.map(empDO, EmpVO.class);
			DeptVO deptVO = modelMapper.map(empDO.getDeptDO(), DeptVO.class);
			empVO.setDeptVO(deptVO);
			return empVO;
		}).collect(Collectors.toList());

//		    return empDOs.stream().map(empDO -> {
//				EmpVO empVO = new EmpVO();
//				empVO.setEmpno(empDO.getEmpno());
//				empVO.setEname(empDO.getEname());
//				empVO.setJob(empDO.getJob());
//				empVO.setHiredate(
//	                    DateTimeFormatter.ofPattern("yyyy-MM-dd")
//	                            .format(empDO.getHiredate())
//	            );
//				empVO.setSal(empDO.getSal());
//				empVO.setComm(empDO.getComm());
//				return empVO;
//			}).collect(Collectors.toList());
	}

	public static DeptVO convertToDeptVO(DeptDO deptDO) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.createTypeMap(EmpDO.class, EmpVO.class);

		DeptVO deptVO = modelMapper.map(deptDO, DeptVO.class);
		List<EmpVO> empVOs = deptDO.getEmpDOs().stream().map(empDO -> modelMapper.map(empDO, EmpVO.class))
				.collect(Collectors.toList());
		deptVO.setEmpVOs(empVOs);
		return deptVO;
	}

	public static EmpVO convertToEmpVO(EmpDO empDO) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.createTypeMap(DeptDO.class, DeptVO.class);

		EmpVO empVO = modelMapper.map(empDO, EmpVO.class);
		DeptVO deptVO = modelMapper.map(empDO.getDeptDO(), DeptVO.class);
		empVO.setDeptVO(deptVO);
		return empVO;
	}
}
