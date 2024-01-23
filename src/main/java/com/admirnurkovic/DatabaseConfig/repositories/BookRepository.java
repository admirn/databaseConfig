package com.admirnurkovic.DatabaseConfig.repositories;

import com.admirnurkovic.DatabaseConfig.domain.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, String> {

}
