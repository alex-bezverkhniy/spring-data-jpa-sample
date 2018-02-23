package com.alexbezverkhniy.samples.springdatajpasample.controllers;

import com.alexbezverkhniy.samples.springdatajpasample.domain.Task;
import com.alexbezverkhniy.samples.springdatajpasample.domain.TodoList;
import com.alexbezverkhniy.samples.springdatajpasample.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Alex Bezverkhniy on 2/22/18.
 */
@RestController
@RequestMapping("/api")
public class TodoController {

    @Autowired
    private TodoService service;

    @GetMapping("/todos/{todoListId}")
    public TodoList findTodoListById(@PathVariable("todoListId") Long todoListId) {
        return service.findTodoList(todoListId);
    }

    @GetMapping("/tasks/{taskId}")
    public Task findTaskById(@PathVariable("taskId") Long taskId) {
        return service.findTask(taskId);
    }

}
