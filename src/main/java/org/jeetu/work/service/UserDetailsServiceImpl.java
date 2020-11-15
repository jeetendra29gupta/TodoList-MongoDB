package org.jeetu.work.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jeetu.work.model.Users;
import org.jeetu.work.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	@Autowired
	UsersRepository usersRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<Users> user = usersRepository.findByUserName(userName);
		if (user.isPresent()) {
			Users userExist = user.get();
			if (userExist.isActive()) {
				List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
				for (String role : userExist.getRoles().split(",")) {
					GrantedAuthority authority = new SimpleGrantedAuthority(role);
					grantedAuthorities.add(authority);
				}
				UserDetails userDetails = new User(userExist.getUserName(), userExist.getPassword(),
						grantedAuthorities);
				return userDetails;
			} else {
				log.error("User " + userName + " is not ACTIVITE");
				throw new BadCredentialsException("User " + userName + " is not ACTIVITE");
			}
		} else {
			log.error("User " + userName + " was not found in the database");
			throw new BadCredentialsException("User " + userName + " was not found in the database");
		}
	}

	public String getLoggedInUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}
		return principal.toString();
	}
}