package com.admirnurkovic.DatabaseConfig.dao;

import com.admirnurkovic.DatabaseConfig.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    void create(Author author);
    Optional<Author> findOne(Long id);

    List<Author> find();

    void update(Long id, Author author);

    void delete(long id);
}
