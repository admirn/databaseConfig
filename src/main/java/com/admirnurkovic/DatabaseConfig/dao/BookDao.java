package com.admirnurkovic.DatabaseConfig.dao;

import com.admirnurkovic.DatabaseConfig.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    void create(Book book);
    Optional<Book> findOne(String isbn);

    List<Book> find();
}
