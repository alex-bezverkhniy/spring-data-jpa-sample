package com.alexbezverkhniy.samples.springdatajpasample.repositories;

import com.alexbezverkhniy.samples.springdatajpasample.domain.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Alex Bezverkhniy on 2/21/18.
 */
public interface ItemRepository extends CrudRepository<Item, Long>, PagingAndSortingRepository<Item, Long> {
    List<Item> findByTitle(String title);
    List<Item> findByTitle(String title, Pageable pageable);
}
