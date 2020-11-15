package org.jeetu.work.todo.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.jeetu.work.toto.model.Todo;
import org.jeetu.work.toto.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:9090")
@RestController
@RequestMapping("/api/v1")
public class TodoRestController {
	@Autowired
	TodoRepository todoRepository;

	@GetMapping("/todo")
	public ResponseEntity<List<Todo>> getAllTodoList() {
		try {
			return new ResponseEntity<>(todoRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/todo/{id}")
	public ResponseEntity<Todo> getTodoById(@PathVariable("id") String id) {
		Optional<Todo> todoData = todoRepository.findById(id);
		try {
			if (todoData.isPresent()) {
				return new ResponseEntity<>(todoData.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/todo")
	public ResponseEntity<Todo> saveTodo(@RequestBody Todo todo) {
		try {
			Todo _todo = new Todo(todo.getId(), todo.getTask(), false, new Date(), null);
			return new ResponseEntity<>(todoRepository.save(_todo), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/todo/{id}")
	public ResponseEntity<Todo> updateTodo(@PathVariable("id") String id, @RequestBody Todo todo) {
		Optional<Todo> todoData = todoRepository.findById(id);
		try {
			if (todoData.isPresent()) {
				Todo _todo = todoData.get();
				_todo.setCompleted(true);
				_todo.setAdded(null);
				_todo.setFinished(new Date());
				return new ResponseEntity<>(todoRepository.save(_todo), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/todo/{id}")
	public ResponseEntity<HttpStatus> deleteTodo(@PathVariable("id") String id) {
		Optional<Todo> todoData = todoRepository.findById(id);
		try {
			if (todoData.isPresent()) {
				todoRepository.deleteById(id);
				return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/todo")
	public ResponseEntity<HttpStatus> deleteAllTodo() {
		try {
			todoRepository.deleteAll();
			return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/todo/completed/{completed}")
	public ResponseEntity<List<Todo>> findByCompleted(@PathVariable("completed") Boolean completed) {
		try {
			return new ResponseEntity<>(todoRepository.findByCompleted(completed), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
