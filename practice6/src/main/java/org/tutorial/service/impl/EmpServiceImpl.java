package org.tutorial.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.tutorial.model.DTO.DeptDTO;
import org.tutorial.model.DTO.EmpDTO;
import org.tutorial.model.PO.DeptPO;
import org.tutorial.model.PO.EmpPO;
import org.tutorial.repository.EmpRepository;
import org.tutorial.service.EmpService;

@Service
@Transactional
public class EmpServiceImpl implements EmpService {
	@Autowired
	private EmpRepository repository;

	@Override
	public EmpDTO addEmp(EmpPO empPO) {
		return convertEmpToDTO(repository.save(empPO));
	}

	@Override
	@CachePut(value = "empCache", key = "#empPO.empno")
	public EmpDTO updateEmp(EmpPO empPO) {
		if (repository.findById(empPO.getEmpno()).isPresent()) { // Optional不能用==null來判斷
			repository.save(empPO);
		}
		return convertEmpToDTO(repository.findById(empPO.getEmpno()).orElse(null)); // Optional需要處理not Present的狀況
	}

	@Override
	@CacheEvict(value = "empCache", key = "#empno")
	public void deleteEmp(Integer empno) {
		if (repository.findById(empno).isPresent()) { // Optional不能用==null來判斷
			repository.deleteById(empno);
		}
	}

	@Override
	@Cacheable(value = "empCache", key = "#empno")
	public EmpDTO getOneEmp(Integer empno) {
		return convertEmpToDTO(repository.findById(empno).orElse(null));
	}

	@Override
	@Cacheable(value = "empCache")
	public List<EmpDTO> getAll() {
		List<EmpPO> empPOs = repository.findAll();
		return empPOs.stream().map(empPO -> convertEmpToDTO(empPO)).collect(Collectors.toList());
	}

	@Override
	@Cacheable(value = "empCache", key = "'page' + #pageNumber + #pageSize + #sortField + #sortDirection")
	public Page<EmpDTO> getEmpsByPageAndSort(int pageNumber, int pageSize, String sortField,
			Sort.Direction sortDirection) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortField));
		Page<EmpPO> empPOPage = repository.findAll(pageable);
		return empPOPage.map(empPO -> convertEmpToDTO(empPO));
	}

	@Override
	@Cacheable(value = "empByDeptnoCache", key = "#deptno")
	public List<EmpDTO> getEmpsByDeptno(Integer deptno) {
		List<EmpPO> empPOs = repository.findByDeptPODeptno(deptno);
		return empPOs.stream().map(empPO -> convertEmpToDTO(empPO)).collect(Collectors.toList());
	}

	// 使用DTO要注意，關聯關係是否有輸出的必要
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

		if (empPO.getDeptPO() != null) {
			empDTO.setDeptDTO(convertDeptToDTO(empPO.getDeptPO()));
		}

		return empDTO;
	}

	public DeptDTO convertDeptToDTO(DeptPO deptPO) {
		if (deptPO == null) {
			return null;
		}
		DeptDTO deptDTO = new DeptDTO();
		deptDTO.setDeptno(deptPO.getDeptno());
		deptDTO.setDname(deptPO.getDname());
		deptDTO.setLoc(deptPO.getLoc());
		// 會有遞迴問題
//		List<EmpDTO> empDTOs = deptPO.getEmpPOs()
//				.stream()
//				.map(this::convertEmpPOToDTO)
//				.collect(Collectors.toList());
//		deptDTO.setEmpDTOs(empDTOs);
		return deptDTO;
	}

}
