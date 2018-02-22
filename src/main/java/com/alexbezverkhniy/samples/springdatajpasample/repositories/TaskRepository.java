package com.alexbezverkhniy.samples.springdatajpasample.repositories;

import com.alexbezverkhniy.samples.springdatajpasample.domain.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alex Bezverkhniy on 2/21/18.
 */
@Repository
public interface TaskRepository extends CrudRepository<Task, Long>, PagingAndSortingRepository<Task, Long> {
    List<Task> findByTitle(String title);
    List<Task> findByTitle(String title, Pageable pageable);
}
