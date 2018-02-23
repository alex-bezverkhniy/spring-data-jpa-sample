package com.alexbezverkhniy.samples.springdatajpasample.services;

import com.alexbezverkhniy.samples.springdatajpasample.domain.Task;
import com.alexbezverkhniy.samples.springdatajpasample.domain.TodoList;
import com.alexbezverkhniy.samples.springdatajpasample.repositories.TaskRepository;
import com.alexbezverkhniy.samples.springdatajpasample.repositories.TodoListRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by Alex Bezverkhniy on 2/21/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoServiceIntegTest {

    @Autowired
    private TodoService service;

    @Autowired
    private TodoListRepository todoListRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void saveItemShouldCreateNewItemInDBTest() {
        Task task = new Task("task #1", "simple task #1", false);
        Task actual = service.saveTask(task);
        assertNotNull(actual);
        assertNotNull(actual.getId());
    }


    @Test(expected = IllegalArgumentException.class)
    public void saveItemShouldThrowExceptionIfNullArgsTest() {
        Task actual = service.saveTask(null);
        assertNull(actual);
    }

    @Test
    @Transactional
    public void addItemToTodoListShouldCreateNewItemInDBTest() {
        TodoList todoList = todoListRepository.save(new TodoList());

        Task task = taskRepository.save(new Task("task #1", "simple task #1", false));
        service.addTaskToTodoList(task, todoList);

        TodoList actual = todoListRepository.findOne(todoList.getId());

        assertNotNull(actual);
        assertNotNull(actual.getId());
        assertNotNull(actual.getTasks());
        assertTrue(actual.getTasks().contains(task));
    }

    @Test
    @Transactional
    public void addItemToTodoList2ShouldCreateNewItemInDBTest() {
        TodoList todoList = todoListRepository.save(new TodoList());

        Task task = taskRepository.save(new Task("task #1", "simple task #1", false));
        service.addTaskToTodoList(task.getId(), todoList.getId());

        TodoList actual = todoListRepository.findOne(todoList.getId());

        assertNotNull(actual);
        assertNotNull(actual.getId());
        assertNotNull(actual.getTasks());
        assertTrue(actual.getTasks().contains(task));
    }

    //TODO test update methods
}
