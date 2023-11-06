package org.tutorial.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.tutorial.model.PO.RolePO;
import org.tutorial.model.PO.UserPO;
import org.tutorial.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserPO user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Can't find user: " + username);
		}

		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		for (RolePO role : user.getRolePOs()) {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		}

		// 基本建構子
//		return new User(user.getUsername(), user.getPassword(), authorities);

//		enabled 是否可用
//		accountNonExpired 帳戶是否失效
//		credentialsNonExpired 密碼是否失效
//		accountNonLocked 帳戶是否鎖定
		return new User(user.getUsername(), user.getPassword(), user.getStatus() == 1, true, true, true, authorities);
	}
}
