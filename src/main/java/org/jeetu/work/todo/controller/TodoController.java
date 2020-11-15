package org.jeetu.work.todo.controller;

import java.util.Date;
import java.util.Optional;

import org.jeetu.work.toto.model.Todo;
import org.jeetu.work.toto.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@CrossOrigin(origins = "http://localhost:9090")
@Controller
public class TodoController {
	@Autowired
	TodoRepository todoRepository;
	
	@GetMapping("/")
	public ModelAndView index(ModelAndView mv) {
		mv.addObject("todo", new Todo());
		mv.addObject("todoList", todoRepository.findAll());
		mv.setViewName("index");
		return mv;
	}
	
	@PostMapping("/saveTodo")
	public ModelAndView saveTodo(Todo todo, ModelAndView mv) {
		todo.setCompleted(false);
		todo.setAdded(new Date());
		todoRepository.save(todo);
		mv.setViewName("redirect:/");
		return mv;
	}
	
	@GetMapping("/deleteTodo/{id}")
	public ModelAndView deleteTodo(ModelAndView mv, @PathVariable String id) {
		todoRepository.deleteById(id);
		mv.setViewName("redirect:/");
		return mv;
	}
	
	@GetMapping("/doneTodo/{id}")
	public ModelAndView doneTodo(ModelAndView mv, @PathVariable String id) {
		Optional<Todo> todo = todoRepository.findById(id);
		if(todo.isPresent()) {
			todo.get().setCompleted(true);
			todo.get().setAdded(null);
			todo.get().setFinished(new Date());
			todoRepository.save(todo.get());
		}
		mv.setViewName("redirect:/");
		return mv;
	}
	
	@GetMapping("/updateTodo/{id}")
	public ModelAndView updateTodo(ModelAndView mv, @PathVariable String id) {
		Optional<Todo> todo = todoRepository.findById(id);
		if(todo.isPresent()) {
			todo.get().setCompleted(false);
			todo.get().setAdded(new Date());
			todo.get().setFinished(null);
			todoRepository.save(todo.get());
		}
		mv.setViewName("redirect:/");
		return mv;
	}
}
