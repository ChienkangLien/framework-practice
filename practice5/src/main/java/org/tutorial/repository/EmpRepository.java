package org.tutorial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tutorial.model.PO.EmpPO;

@Repository
public interface EmpRepository extends JpaRepository<EmpPO, Integer> {
	public List<EmpPO> findByDeptPODeptno(Integer deptno);
}
