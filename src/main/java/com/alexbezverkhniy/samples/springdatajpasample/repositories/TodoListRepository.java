package com.alexbezverkhniy.samples.springdatajpasample.repositories;

import com.alexbezverkhniy.samples.springdatajpasample.domain.Task;
import com.alexbezverkhniy.samples.springdatajpasample.domain.TodoList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alex Bezverkhniy on 2/21/18.
 */
@Repository
public interface TodoListRepository extends CrudRepository<TodoList, Long>, PagingAndSortingRepository<TodoList, Long> {
    List<Task> findByTitle(String title);
    List<Task> findByTitle(String title, Pageable pageable);
}
