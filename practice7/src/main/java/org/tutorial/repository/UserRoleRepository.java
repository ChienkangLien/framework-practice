package org.tutorial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tutorial.model.PO.UserRolePO;
import org.tutorial.model.PO.embeddings.UserRoleId;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRolePO, UserRoleId> {

}
