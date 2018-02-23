package com.alexbezverkhniy.samples.springdatajpasample.services;

import com.alexbezverkhniy.samples.springdatajpasample.domain.BaseEntity;
import com.alexbezverkhniy.samples.springdatajpasample.domain.Task;
import com.alexbezverkhniy.samples.springdatajpasample.domain.TodoList;
import com.alexbezverkhniy.samples.springdatajpasample.repositories.TaskRepository;
import com.alexbezverkhniy.samples.springdatajpasample.repositories.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

import java.util.Date;

import static com.alexbezverkhniy.samples.springdatajpasample.SpringDataJpaSampleApplication.DEFAULT_USER;

/**
 * Created by Alex Bezverkhniy on 2/21/18.
 */
@Service
public class TodoService {

    @Autowired
    protected TaskRepository taskRepository;

    @Autowired
    protected TodoListRepository todoListRepository;

    public Task saveTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Argument \"task\" should NOT be null");
        }
        updateOrPopulateMetadata(task);
        task.setComplete(task.getComplete() == null ? false : task.getComplete() );
        return taskRepository.save(task);
    }

    public TodoList saveTodoList(TodoList todoList) {
        if (todoList == null) {
            throw new IllegalArgumentException("Argument \"todoList\" should NOT be null");
        }
        updateOrPopulateMetadata(todoList);
        return todoListRepository.save(todoList);
    }

    @Transactional
    public TodoList addTaskToTodoList(Long taskId, Long todoListId) {
        TodoList todoList = findTodoList(todoListId);
        todoList.getTasks().add(findTask(taskId));
        todoListRepository.save(todoList);
        return todoList;
    }

    @Transactional
    public TodoList addTaskToTodoList(Task task, TodoList todoList) {
        if (task == null) {
            throw new IllegalArgumentException("Argument \"task\" should NOT be null");
        }
        if (task.getId() == null) {
            throw new IllegalArgumentException("Argument \"task\" is NOT JPA entity");
        }

        if (todoList == null) {
            throw new IllegalArgumentException("Argument \"todoList\" should NOT be null");
        }
        if (todoList.getId() == null) {
            throw new IllegalArgumentException("Argument \"todoList\" is NOT JPA entity");
        }

        todoList.getTasks().add(task);
        todoListRepository.save(todoList);
        return todoList;
    }

    public boolean togleTaskComplete(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Argument \"taskId\" should NOT be null");
        }

        return togleTaskComplete(task.getId());
    }
    public boolean togleTaskComplete(Long taskId) {
        Task task = findTask(taskId);
        task.setComplete(!task.getComplete());
        taskRepository.save(task);
        return task.getComplete();
    }

    public TodoList findTodoList(Long todoListId) {

        if (todoListId == null) {
            throw new IllegalArgumentException("Argument \"todoListId\" should NOT be null");
        }

        TodoList todoList = todoListRepository.findOne(todoListId);
        if (todoList == null) {
            throw new TodoListNotFoundException(todoListId);
        }

        return todoList;
    }

    public TodoList findTodoList(TodoList todoList) {

        if (todoList == null) {
            throw new IllegalArgumentException("Argument \"todoList\" should NOT be null");
        }

        return findTodoList(todoList.getId());
    }

    public Task findTask(Long taskId) {
        if (taskId == null) {
            throw new IllegalArgumentException("Argument \"taskId\" should NOT be null");
        }

        Task task = taskRepository.findOne(taskId);
        if (task == null) {
            throw new TaskNotFoundException(taskId);
        }

        return task;
    }

    public Task findTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Argument \"task\" should NOT be null");
        }

        return findTask(task.getId());
    }

    public void deleteTodoList(TodoList todoList) {
        todoListRepository.delete(findTodoList(todoList));
    }

    public void deleteTodoList(Long todoListId) {
        todoListRepository.delete(findTodoList(todoListId));
    }

    public void deleteTask(Task task) {
        taskRepository.delete(findTask(task));
    }

    public void deleteTask(Long todoListId) {
        taskRepository.delete(findTask(todoListId));
    }

    public Page<Task> findTaskByTitle(String title, int page, int size) {
        Page<Task> result = taskRepository.findByTitle(title, new PageRequest(page, size));
        if (result == null) {
            throw new TaskNotFoundException(String.format("Task with title: %s is not found", title));
        }
        return result;
    }

    public Page<TodoList> findTodoListByTitle(String title, int page, int size) {
        Page<TodoList> result = todoListRepository.findByTitle(title, new PageRequest(page, size));
        if (result == null) {
            throw new TodoListNotFoundException(String.format("Todo list with title: %s is not found", title));
        }
        return result;
    }

    public Page<TodoList> findAllTodoLists(PageRequest pageRequest) {
        if (pageRequest == null) {
            throw new IllegalArgumentException("Argument \"pageRequest\" should NOT be null");
        }
        Page<TodoList> result = todoListRepository.findAll(pageRequest);
        if (result == null) {
            throw new TodoListNotFoundException();
        }
        return result;
    }

    public Page<Task> findAllTasks(PageRequest pageRequest) {
        if (pageRequest == null) {
            throw new IllegalArgumentException("Argument \"pageRequest\" should NOT be null");
        }
        Page<Task> result = taskRepository.findAll(pageRequest);

        if (result == null) {
            throw new TaskNotFoundException();
        }

        return result;
    }

    protected void updateOrPopulateMetadata(BaseEntity entity) {
        entity.setCreatedAt(entity.getCreatedAt() == null ? new Date() : entity.getCreatedAt());
        entity.setCreatedBy(StringUtils.isEmpty(entity.getCreatedBy()) ? DEFAULT_USER : entity.getCreatedBy());
        entity.setUpdatedAt(new Date());
        entity.setUpdatedBy(StringUtils.isEmpty(entity.getUpdatedBy()) ? DEFAULT_USER : entity.getUpdatedBy());
    }
}
