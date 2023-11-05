package org.tutorial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tutorial.model.PO.RolePO;
@Repository
public interface RoleRepository extends JpaRepository<RolePO, Integer> {

}
