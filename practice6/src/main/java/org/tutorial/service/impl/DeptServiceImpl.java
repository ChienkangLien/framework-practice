package org.tutorial.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.tutorial.model.DTO.DeptDTO;
import org.tutorial.model.DTO.EmpDTO;
import org.tutorial.model.PO.DeptPO;
import org.tutorial.model.PO.EmpPO;
import org.tutorial.repository.DeptRepository;
import org.tutorial.service.DeptService;

@Service
@Transactional
public class DeptServiceImpl implements DeptService {
	@Autowired
	private DeptRepository repository;

	@Override
	@Cacheable("deptCache")
	public List<DeptDTO> getAll() {
		List<DeptPO> deptPOs = repository.findAll();
		return deptPOs.stream().map(deptPO -> convertDeptToDTO(deptPO)).collect(Collectors.toList());
	}

	@Override
	@CachePut(value = "deptCache", key = "#deptPO.deptno")
	public DeptDTO update(DeptPO deptPO) {
		if (repository.findById(deptPO.getDeptno()).isPresent()) { // Optional不能用==null來判斷
			repository.save(deptPO);
		}
		return convertDeptToDTO(repository.findById(deptPO.getDeptno()).orElse(null)); // Optional需要處理not Present的狀況
	}

	@Override
	@Cacheable(value = "deptCache", key = "#deptno")
	public DeptDTO getOneDept(Integer deptno) {
		return convertDeptToDTO(repository.findById(deptno).orElse(null));
	}

	@Override
	@CacheEvict(value = "deptCache", key = "#deptno")
	public void deleteDept(Integer deptno) {
		if (repository.findById(deptno).isPresent()) { // Optional不能用==null來判斷
			repository.deleteById(deptno);
		}
	}

	@Override
	public DeptDTO insert(DeptPO deptPO) {
		return convertDeptToDTO(repository.save(deptPO));
	}

	// 使用DTO要注意，關聯關係是否有輸出的必要
	public DeptDTO convertDeptToDTO(DeptPO deptPO) {
		if (deptPO == null) {
			return null;
		}
		DeptDTO deptDTO = new DeptDTO();
		deptDTO.setDeptno(deptPO.getDeptno());
		deptDTO.setDname(deptPO.getDname());
		deptDTO.setLoc(deptPO.getLoc());
		if (deptPO.getEmpPOs() != null) {
			List<EmpDTO> empDTOs = deptPO.getEmpPOs().stream().map(this::convertEmpToDTO).collect(Collectors.toList());
			deptDTO.setEmpDTOs(empDTOs);
		}
		return deptDTO;
	}

	public EmpDTO convertEmpToDTO(EmpPO empPO) {
		if (empPO == null) {
			return null;
		}
		EmpDTO empDTO = new EmpDTO();
		empDTO.setEmpno(empPO.getEmpno());
		empDTO.setEname(empPO.getEname());
		empDTO.setJob(empPO.getJob());
		empDTO.setHiredate(empPO.getHiredate());
		empDTO.setSal(empPO.getSal());
		empDTO.setComm(empPO.getComm());
		// 會有遞迴問題
//	    empDTO.setDeptDTO(convertDeptToDTO(empPO.getDeptPO()));

		return empDTO;
	}

}
