package org.tutorial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tutorial.model.PO.PermissionPO;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionPO, Integer> {

}
