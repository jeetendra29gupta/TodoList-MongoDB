package org.jeetu.work.repository;

import java.util.Optional;

import org.jeetu.work.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
	Optional<Users> findByUserName(String userName);
}
