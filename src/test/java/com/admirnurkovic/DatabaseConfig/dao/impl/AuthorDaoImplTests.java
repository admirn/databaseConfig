package com.admirnurkovic.DatabaseConfig.dao.impl;

import com.admirnurkovic.DatabaseConfig.TestDataUtils;
import com.admirnurkovic.DatabaseConfig.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthorDaoImplTests
{
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDaoImpl underTest;

    @Test
    public void testCreationAuthorSql(){
        Author author = TestDataUtils.creteTestAuthor();

        underTest.create(author);

        verify(jdbcTemplate).update(
                eq("INSERT INTO authors(id, name, age) VALUES (?, ?, ?)"),
                eq(1l), eq("Admir Nurkovic"), eq(80)
                );
    }

    @Test
    public void testGetOneAuthorTest(){
        underTest.findOne(1L);

        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors where id = ? LIMIT 1"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any(),
                eq(1l)
        );


    }

    @Test
    public void testGetManyAuthorsSql(){
        underTest.find();

        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any()
        );
    }


    @Test
    public void testUpdateAuthorSql(){
        Author author = TestDataUtils.creteTestAuthor();
        underTest.update(1L, author);

        verify(jdbcTemplate).update(
                "UPDATE authors SET id = ?, name = ?, age = ? WHERE id = ?",
                1L, "Admir Nurkovic", 80, 1L
        );
    }


    @Test
    public void testDeleteAuthorSql(){
        underTest.delete(1L);
        verify(jdbcTemplate).update(
                "DELETE FROM authors WHERE id = ?",
                1L
        );
    }



}
