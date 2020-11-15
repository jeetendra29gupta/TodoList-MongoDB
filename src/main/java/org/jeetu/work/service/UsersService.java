package org.jeetu.work.service;

import java.util.List;
import java.util.Optional;

import org.jeetu.work.model.Users;
import org.jeetu.work.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
	@Autowired
	private UsersRepository usersRepository;

	public void saveUser(Users user) {
		usersRepository.save(user);
	}

	public List<Users> getAllUser() {
		return usersRepository.findAll();
	}

	public Optional<Users> getUser(String userName) {
		return usersRepository.findByUserName(userName);
	}

	public Optional<Users> getUserByID(long id) {
		return usersRepository.findById(id);
	}

	public void deleteUser(long id) {
		usersRepository.deleteById(id);
	}
}
