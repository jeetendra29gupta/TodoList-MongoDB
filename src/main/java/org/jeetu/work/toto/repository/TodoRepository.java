package org.jeetu.work.toto.repository;

import java.util.List;

import org.jeetu.work.toto.model.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TodoRepository extends MongoRepository<Todo, String>{
	List<Todo> findByCompleted(boolean completed);
}
