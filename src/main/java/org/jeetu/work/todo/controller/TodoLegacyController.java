package org.jeetu.work.todo.controller;

import java.util.Date;

import org.jeetu.work.toto.model.Todo;
import org.jeetu.work.toto.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/api/v1")
public class TodoLegacyController {
	@Autowired
	private TodoService todoService;

	@GetMapping("/")
	public ModelAndView index(ModelAndView mv) {
		mv.addObject("todo", new Todo());
		mv.addObject("todoList", todoService.getAllTodo());
		mv.setViewName("index");
		return mv;
	}

	@PostMapping("/saveTodo")
	public ModelAndView saveTodo(Todo todo, ModelAndView mv) {
		todo.setCompleted(false);
		todo.setAdded(new Date());
		todoService.saveTodo(todo);
		mv.setViewName("redirect:/");
		return mv;
	}
	
	@GetMapping("/doneTodo/{id}")
	public ModelAndView doneTodo(ModelAndView mv, @PathVariable String id) {
		todoService.doneTodo(id);
		mv.setViewName("redirect:/");
		return mv;
	}
	
	@GetMapping("/updateTodo/{id}")
	public ModelAndView updateTodo(ModelAndView mv, @PathVariable String id) {
		todoService.updateTodo(id);
		mv.setViewName("redirect:/");
		return mv;
	}
	
	@GetMapping("/deleteTodo/{id}")
	public ModelAndView deleteTodo(ModelAndView mv, @PathVariable String id) {
		todoService.deleteTodo(id);
		mv.setViewName("redirect:/");
		return mv;
	}
}
