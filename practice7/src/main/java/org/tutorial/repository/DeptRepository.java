package org.tutorial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tutorial.model.PO.DeptPO;

@Repository
public interface DeptRepository extends JpaRepository<DeptPO, Integer> {

}
