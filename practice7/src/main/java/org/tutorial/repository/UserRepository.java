package org.tutorial.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.tutorial.model.PO.UserPO;

@Repository
public interface UserRepository extends JpaRepository<UserPO, Integer> {

	public UserPO findByUsernameAndPassword(String username, String password);

	public UserPO findByUsername(String username);
}
