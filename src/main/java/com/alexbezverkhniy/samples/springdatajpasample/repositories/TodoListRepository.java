package com.alexbezverkhniy.samples.springdatajpasample.repositories;

import com.alexbezverkhniy.samples.springdatajpasample.domain.Item;
import com.alexbezverkhniy.samples.springdatajpasample.domain.TodoList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Alex Bezverkhniy on 2/21/18.
 */
public interface TodoListRepository extends CrudRepository<TodoList, Long>, PagingAndSortingRepository<TodoList, Long> {
    List<Item> findByTitle(String title);
    List<Item> findByTitle(String title, Pageable pageable);
}
