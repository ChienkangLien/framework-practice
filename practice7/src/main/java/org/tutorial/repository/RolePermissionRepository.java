package org.tutorial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tutorial.model.PO.RolePermissionPO;
import org.tutorial.model.PO.embeddings.RolePermissionId;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermissionPO, RolePermissionId> {

}
