package com.alexbezverkhniy.samples.springdatajpasample.services;

import com.alexbezverkhniy.samples.springdatajpasample.domain.Task;
import com.alexbezverkhniy.samples.springdatajpasample.domain.TodoList;
import com.alexbezverkhniy.samples.springdatajpasample.repositories.TaskRepository;
import com.alexbezverkhniy.samples.springdatajpasample.repositories.TodoListRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Created by Alex Bezverkhniy on 2/22/18.
 */
@RunWith(MockitoJUnitRunner.class)
public class TodoServiceTest {
    private TodoService service;

    @Mock
    private TodoListRepository todoListRepository;

    @Mock
    private TaskRepository taskRepository;


    @Before
    public void before() {
        service = new TodoService();
        service.todoListRepository = todoListRepository;
        service.taskRepository = taskRepository;
    }

    @Test
    public void saveTaskShouldSaveInDBTest() {
        final Task task = new Task();

        when(taskRepository.save(task)).then(invocation -> {
            task.setId(1l);
            return task;
        });

        Task actual = service.saveTask(task);

        verify(taskRepository, times(1)).save(task);
        assertNotNull(actual.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveTaskShouldThrowIllegalArgumentExceptionTest() {
        service.saveTask(null);
    }
    @Test
    public void saveTodoShouldSaveInDBTest() {
        final TodoList todoList = new TodoList();

        when(todoListRepository.save(todoList)).then(invocation -> {
            todoList.setId(1l);
            return todoList;
        });

        TodoList actual = service.saveTodoList(todoList);

        verify(todoListRepository, times(1)).save(todoList);
        assertNotNull(actual.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteTaskShouldThrowIllegalArgumentException1Test() {
        TodoList todoList = null;
        service.deleteTodoList(todoList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteTaskShouldThrowIllegalArgumentException2Test() {
        final TodoList todoList = new TodoList();
        service.deleteTodoList(todoList);
    }

    @Test
    public void deleteTodoShouldRemoveFromDBTest() {
        final TodoList todoList = new TodoList();
        todoList.setId(1L);

        when(todoListRepository.findOne(anyLong())).thenReturn(todoList);

        service.deleteTodoList(todoList);

        verify(todoListRepository, times(1)).delete(todoList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveTodoShouldThrowIllegalArgumentExceptionTest() {
        service.saveTodoList(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTaskToTodoList2ShouldThrowIllegalArgumentException1Test() {
        service.addTaskToTodoList(1L, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTaskToTodoList2ShouldThrowIllegalArgumentException2Test() {
        when(todoListRepository.findOne(anyLong())).thenReturn(new TodoList());
        service.addTaskToTodoList(null, 1L);
    }

    @Test(expected = TodoListNotFoundException.class)
    public void addTaskToTodoList2ShouldThrowTodoListNotFoundExceptionTest() {
        when(taskRepository.findOne(2L)).thenReturn(new Task());
        service.addTaskToTodoList(2L, 1L);
    }

    @Test(expected = TaskNotFoundException.class)
    public void addTaskToTodoList2ShouldThrowTaskNotFoundExceptionTest() {
        when(todoListRepository.findOne(anyLong())).thenReturn(new TodoList());
        when(taskRepository.findOne(1L)).thenReturn(null);
        service.addTaskToTodoList(1L, 1L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTaskToTodoListShouldThrowIllegalArgumentException1Test() {
        service.addTaskToTodoList(null, new TodoList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTaskToTodoListShouldThrowIllegalArgumentException2Test() {
        service.addTaskToTodoList(new Task(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTaskToTodoListShouldThrowIllegalArgumentException3Test() {
        TodoList todoList = new TodoList();
        todoList.setId(1L);
        service.addTaskToTodoList(new Task(), todoList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTaskToTodoListShouldThrowIllegalArgumentException4Test() {
        Task task = new Task();
        task.setId(1L);
        service.addTaskToTodoList(task, new TodoList());
    }

    @Test
    public void addTaskToTodoListShouldAddAndSaveTest() {
        TodoList todoList = new TodoList();
        todoList.setId(1L);
        Task task = new Task();
        task.setId(1L);

        service.addTaskToTodoList(task, todoList);

        assertTrue(todoList.getTasks().contains(task));
        verify(todoListRepository, times(1)).save(todoList);
    }


    @Test(expected = IllegalArgumentException.class)
    public void completeTaskShouldThrowIllegalArgumentException1Test() {
        Long id = null;
        service.togleTaskComplete(id);
    }

    @Test(expected = IllegalArgumentException.class)
    public void completeTaskShouldThrowIllegalArgumentException2Test() {
        Task task = null;
        service.togleTaskComplete(task);
    }

    @Test(expected = IllegalArgumentException.class)
    public void completeTaskShouldThrowIllegalArgumentException3Test() {
        Task task = new Task();
        service.togleTaskComplete(task);
    }

    @Test
    public void completeTaskShouldCompleteTaskTest() {
        Task task = new Task();
        task.setComplete(false);
        when(taskRepository.findOne(1L)).thenReturn(task);

        boolean actual = service.togleTaskComplete(1L);

        assertTrue(actual);
        verify(taskRepository, times(1)).save(task);
    }
}
